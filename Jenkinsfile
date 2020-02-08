pipeline {

    agent any

    stages {
        stage('Gradle Build') {
            if (isUnix()) {
                sh './gradlew clean build'
            } else {
                bat 'gradlew.bat clean build'
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
