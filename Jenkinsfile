pipeline {
    agent any

    environment {
        MYSQL_PASSWORD = credentials('NEXCHANGE_USERSERVICE_MYSQL_PASSWORD') // stored in Jenkins Server
        JWT_SECRET = credentials('NEXCHANGE_USERSERVICE_JWT_SECRET') // stored in Jenkins Server
        DOCKER_CREDENTIALS = 'docker_hub_credentials' // stored in Jenkins Server
        DOCKER_IMAGE = "jmx7139/nexchange-orderservice"
    }

    parameters {
        string(name: 'JAR_NAME', defaultValue: 'NexChange-OrderService', description: 'The name of the JAR file')
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build and Package') {
            steps {
                script {
                    sh "mvn clean package -DskipTests"
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    sh "docker build -t $DOCKER_IMAGE ."
                }
            }
        }

        stage('Push Docker Image') {
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: 'docker_hub_credentials', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
                        sh "docker login -u $USERNAME -p $PASSWORD"
                        sh "docker tag $DOCKER_IMAGE $DOCKER_IMAGE:latest"
                        sh "docker push $DOCKER_IMAGE:latest"
                        sh "docker images"
                    }
                }
            }
        }

        stage('Verify Push') {
            steps {
                script {
                    sh "docker pull $DOCKER_IMAGE:latest"
                    sh "docker images"
                }
            }
        }
        stage('Cleanup Docker Images') {
            steps {
                script {
                    // 删除超过2个的镜像，保留最新的
                    sh '''
                docker images --format "{{.ID}} {{.Repository}} {{.Tag}} {{.CreatedAt}}" | grep $DOCKER_IMAGE | sort -r | awk 'NR>2 {print $1}' | xargs -r docker rmi
            '''
                    sh "docker images"
                }
            }
        }

        stage('Verify Kubernetes Access') {
            steps {
                script {
                    sh "kubectl config view"
                    def canGetPods = sh(script: "kubectl auth can-i get pods", returnStatus: true) == 0
                    if (!canGetPods) {
                        error "Jenkins lacks necessary permissions to manage Kubernetes resources"
                    }
                    echo "Kubernetes access verified successfully"
                }
            }
        }

        stage('Apply ConfigMaps and Secrets') {
            steps {
                script {
                    sh 'kubectl apply -f configmap-order.yaml'
                    sh 'kubectl apply -f secrets-order.yaml'
                    echo "ConfigMaps and Secrets applied successfully"
                }
            }
        }

        stage('Verify Dependencies') {
            steps {
                script {
                    // 验证 Zookeeper
                    echo "Verifying Zookeeper..."
                    def zkPod = sh(script: "kubectl get pod -l app=zookeeper -o jsonpath='{.items[0].metadata.name}'", returnStdout: true).trim()
                    if (!zkPod) {
                        error "Zookeeper pod not found. Please ensure Zookeeper is running."
                    } else {
                        echo "Zookeeper is running: ${zkPod}"
                    }

                    // 验证 Kafka
                    echo "Verifying Kafka..."
                    def kafkaPod = sh(script: "kubectl get pod -l app=kafka -o jsonpath='{.items[0].metadata.name}'", returnStdout: true).trim()
                    if (!kafkaPod) {
                        error "Kafka pod not found. Please ensure Kafka is running."
                    } else {
                        echo "Kafka is running: ${kafkaPod}"
                    }

                    // 验证 Redis
                    echo "Verifying Redis..."
                    def redisPod = sh(script: "kubectl get pod -l app=redis -o jsonpath='{.items[0].metadata.name}'", returnStdout: true).trim()
                    if (!redisPod) {
                        error "Redis pod not found. Please ensure Redis is running."
                    } else {
                        echo "Redis is running: ${redisPod}"
                    }
                }
            }
        }

        stage('Create PV and PVC for MySQL') {
            steps {
                script {
                    // 检查PVC是否存在
                    def pvcStatus = sh(
                            script: "kubectl get pvc mysql-pvc-order --ignore-not-found -o jsonpath='{.status.phase}'",
                            returnStdout: true
                    ).trim()

                    if (pvcStatus == "Bound") {
                        echo "PVC 'mysql-pvc-order' already exists, skipping creation."
                    } else {
                        // 如果 PVC 不存在，执行创建
                        echo "PVC 'mysql-pvc-user' not found, creating..."
                        sh "kubectl apply -f mysql-order-service-pv.yaml"
                        sh "kubectl apply -f mysql-order-service-pvc.yaml"

                        // 等待 PVC 准备就绪
//                        sh "kubectl wait --for=condition=bound pvc/mysql-pvc-order --timeout=60s"

                        echo "PV and PVC for MySQL created successfully."
                    }
                }
            }
        }

        stage('Deploy MySQL') {
            steps {
                script {
                    sh "kubectl apply -f mysql-deployment-order.yaml"
                    sh "kubectl wait --for=condition=ready pod -l app=mysql-order-service --timeout=120s"
                    echo "MySQL deployed successfully."

                    // 获取 MySQL Pod 并初始化数据库
                    def mysqlPod = sh(script: "kubectl get pod -l app=mysql -o jsonpath='{.items[0].metadata.name}'", returnStdout: true).trim()
                    sh """
                        echo "Creating OrderService database if not exists..."
                        kubectl exec ${mysqlPod} -- mysql -uroot -padmin -e '
                        CREATE DATABASE IF NOT EXISTS NexChangeOrderDB;
                        SHOW DATABASES;
                        '
                    """
                }
            }
        }

        stage('Deploy Order Service') {
            steps {
                script {
                    // 部署 OrderService
                    sh 'kubectl apply -f deployment-order.yaml'
                    sh "kubectl rollout status deployment/nexchange-orderservice"

                    // 等待 Pod 准备好
                    sh "kubectl wait --for=condition=ready pod -l app=nexchange-orderservice --timeout=180s"

                    // 获取 Pod 状态
                    def podName = sh(
                            script: "kubectl get pod -l app=nexchange-orderservice -o jsonpath='{.items[0].metadata.name}'",
                            returnStdout: true
                    ).trim()

                    // 测试服务连接
                    echo "Testing service connectivity..."
                    sh """
                        echo "Database Connection Info:"
                        kubectl logs ${podName} | grep -i "database"

                        echo "\nKafka Connection Info:"
                        kubectl logs ${podName} | grep -i "kafka"

                        echo "\nRedis Connection Info:"
                        kubectl logs ${podName} | grep -i "redis"

                        echo "\nApplication Health:"
                        kubectl logs ${podName} | grep -i "started"
                    """

                    echo "Application Logs:"
                    sh """
                        echo "Recent Logs:"
                        kubectl logs ${podName} --tail=50

                        echo "\nApplication Status:"
                        kubectl exec ${podName} -- curl -s http://localhost:8082/actuator/health || true
                    """
                }
            }
        }

        stage('Test Service Connections') {
            steps {
                script {
                    def orderServicePod = sh(
                            script: "kubectl get pod -l app=nexchange-orderservice -o jsonpath='{.items[0].metadata.name}'",
                            returnStdout: true
                    ).trim()

                    echo "Testing Service Connections..."

                    // 测试 MySQL 连接
                    sh """
                        echo "Testing MySQL Connection with curl:"
                        kubectl exec ${orderServicePod} -- curl mysql-order-service:3306 || true

                        echo "\nTesting MySQL Authentication:"
                        kubectl exec \$(kubectl get pod -l app=mysql -o jsonpath='{.items[0].metadata.name}') -- \
                        mysql -uroot -padmin -e 'SELECT 1' || true
                    """

                    // 测试 Redis 连接
                    sh """
                        echo "\nTesting Redis Connection with curl:"
                        kubectl exec ${orderServicePod} -- curl redis-service:6379 || true

                        echo "\nTesting Redis Functionality:"
                        kubectl exec \$(kubectl get pod -l app=redis -o jsonpath='{.items[0].metadata.name}') -- \
                        redis-cli ping || true
                    """

                    // 测试 Kafka 连接
                    sh """
                        echo "\nTesting Kafka Connection with curl:"
                        kubectl exec ${orderServicePod} -- curl kafka-service:9092 || true

                        echo "\nTesting Kafka Topics:"
                        kubectl exec \$(kubectl get pod -l app=kafka -o jsonpath='{.items[0].metadata.name}') -- \
                        kafka-topics.sh --bootstrap-server localhost:9092 --list || true
                    """
                }
            }
        }

        stage('Verify Order Service Deployment') {
            steps {
                script {
                    def orderServicePods = sh(script: "kubectl get pods -l app=nexchange-orderservice -o jsonpath='{.items[*].status.phase}'", returnStdout: true).trim()
                    echo "Order Service pods status: ${orderServicePods}"

                    def orderServiceDetails = sh(script: "kubectl describe deployment nexchange-orderservice", returnStdout: true).trim()
                    echo "Order Service Deployment Details:\n${orderServiceDetails}"

                    def orderServiceLogs = sh(script: "kubectl logs -l app=nexchange-orderservice --tail=50", returnStdout: true).trim()
                    echo "Order Service Logs (last 50 lines):\n${orderServiceLogs}"
                }
            }
        }

        stage('Get Cluster IP') {
            steps {
                script {
                    def serviceClusterIP = sh(script: "kubectl get service nexchange-orderservice -o jsonpath='{.spec.clusterIP}'", returnStdout: true).trim()
                    echo "Service ClusterIP: ${serviceClusterIP}"
                }
            }
        }
    }
}
