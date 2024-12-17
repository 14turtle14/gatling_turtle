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
                git 'https://github.com/your-repo/your-project.git'
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
                    sh "${MAVEN_HOME}/bin/mvn test -Dtest=gatling_exec.Executor"
                }
            }
        }
    }

    post {
        always {
            script {
                sh "${MAVEN_HOME}/bin/mvn test -Dtest=gatling_exec.Executor -Dexec.args=stop"
            }
        }
    }
}
