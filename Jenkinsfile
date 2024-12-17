pipeline {
    agent any

    environment {
        MAVEN_HOME = tool 'Maven'
    }

    options {
        timeout(time: 15, unit: 'MINUTES')
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/14turtle14/gatling_turtle.git'
            }
        }
        stage('Build') {
            steps {
                script {
                    sh "${MAVEN_HOME}/bin/mvn clean package"
                }
            }
        }
        stage('Test') {
            steps {
                script {
                    sh "${MAVEN_HOME}/bin/mvn test -Dtest=gatling_exec.ExecutorTest#runGatlingTest"
                }
            }
        }
    }

    post {
        always {
            script {
                sh "${MAVEN_HOME}/bin/mvn exec:java -Dexec.mainClass=gatling_exec.ExecutorTest -Dexec.args=stop"
            }
        }
    }
}
