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

    stage('Checkout') {
                steps {
                    dir("Commons") {
                        git branch: 'development',url: 'git@github.corp.globant.com:BrainWaves/Commons.git'
                    }
                    dir("CoreEngine"){
                        checkout scm
                    }
                }
    }

    stage('build_Project'){
       steps{
            pushd "${workspaceDir}/CoreEngine"
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

    stage("Clean Workspace"){
        steps{
         step([$class: 'WsCleanup'])
        }
    }

  }
  post {
        always {
           slackNotificator(currentBuild.currentResult)
        }
    }
}