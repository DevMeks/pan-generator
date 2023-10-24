pipeline {
    agent { docker { image 'maven:3.9.5-eclipse-temurin-11' } }
    stages {
        stage('build') {
            steps {
                sh 'mvn --version'
            }
        }
    }
}

