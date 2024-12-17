pipeline {
    agent any

    environment {
        MAVEN_HOME = tool 'Maven'
    }

    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/14turtle14/gatling_turtle.git'
            }
        }
        stage('Build') {
            steps {
                script {
                    sh "${MAVEN_HOME}/bin/mvn clean test -Dtest=GatlingWithWireMock"
                }
            }
        }
        stage('Test') {
            steps {
                script {
                    sh "${MAVEN_HOME}/bin/mvn gatling:test"
                }
            }
        }
    }

    post {
        always {
            script {
                sh "${MAVEN_HOME}/bin/mvn clean test -Dtest=GatlingWithWireMock -DstopWireMock=true"
            }
        }
    }
}
