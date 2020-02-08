pipeline {

    agent any

//     triggers {
//         cron('H */8 * * *') //regular builds
//         pollSCM('* * * * *') //polling for changes, here once a minute
//     }

    stages {
        stage('Checkout') {
                git url: 'git@github.corp.globant.com:BrainWaves/CoreEngine.git'
        }
//         stage('Unit & Integration Tests') {
//             steps {
//                 script {
//                     try {
//                         sh './gradlew clean test --no-daemon' //run a gradle task
//                     } finally {
//                         junit '**/build/test-results/test/*.xml' //make the junit test results available in any case (success & failure)
//                     }
//                 }
//             }
//         }

//         stage('Static Code Analysis') {
//             steps {
//                 script {
//                     try {
//                         sh './gradlew tslint --no-daemon'
//                     } finally { //Make checkstyle results available
//                         checkstyle canComputeNew: false, defaultEncoding: '', healthy: '', pattern: 'publicapi/frontend/tslint-result.xml', unHealthy: ''
//                     }
//                 }
//             }
//         }
        stage('Build') {
                    steps {
                        sh './gradlew clean build'
                    }
        }

        stage('Publish Artifact') {
            steps {
                sh './gradlew publish --no-daemon'
            }
        }
    }
    post {
        always { //Send an email to the person that broke the build
            step([$class                  : 'Mailer',
                  notifyEveryUnstableBuild: true,
                  recipients              : [emailextrecipients([[$class: 'CulpritsRecipientProvider'], [$class: 'RequesterRecipientProvider']])].join(' ')])
        }
    }
}
