stage 'build_Project'
node{

  stage 'checkout'

   // Get some code from a GitHub repository
  git url: 'git@github.corp.globant.com:BrainWaves/CoreEngine.git'

  stage 'build'

  if(isUnix()){
  sh './gradlew clean build'

  }
  else{
    bat 'gradlew.bat clean build'
  }
}