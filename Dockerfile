FROM openjdk:8-jdk-alpine
LABEL maintainer="gustavo.peiretti@globant.com"
VOLUME /tmp
EXPOSE 8081
ARG JAR_FILE=build/libs/brainWaves-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} brainWaves-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/brainWaves-0.0.1-SNAPSHOT.jar"]
