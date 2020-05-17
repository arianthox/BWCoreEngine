/* import shared library */
@Library('jenkins-shared-library')_

pipeline {
  environment {
      registry = "brainwaves/core-engine"
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

    stage('Checkout Commons') {
          steps {
            git branch: 'development',
                url: 'git@github.corp.globant.com:BrainWaves/Commons.git'
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
            dockerImage.push("latest")
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
  post {
        always {
           slackNotificator(currentBuild.currentResult)
        }
    }
}