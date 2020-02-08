stage 'build_Project'
node{
  if(isUnix()){
  sh './gradlew clean build'

  }
  else{
    bat 'gradlew.bat clean build'
  }
}