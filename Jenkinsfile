pipeline {
  environment {
      registry = "ricardosanchez/brainwaves"
      registryCredential = 'dockerhub'
      dockerImage = ''
  }
  agent any
  stages {

    stage('Cloning Git') {
      steps {
        checkout scm
      }
    }

    stage('build_Project'){
       steps{
            sh './gradlew clean build'
       }
    }

    stage('Building image') {
      steps{
        script {
          dockerImage = docker.build registry + ":$BUILD_NUMBER"
        }
      }
    }

    stage('Deploy Image') {
      steps{
        script {
          docker.withRegistry( '', registryCredential ) {
            dockerImage.push()
          }
        }
      }
    }

    stage('Remove Unused docker image') {
      steps{
        sh "docker rmi $registry:$BUILD_NUMBER"
      }
    }

  }
}