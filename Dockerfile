FROM openjdk:13-jdk-slim
LABEL maintainer="gustavo.peiretti@globant.com"
VOLUME /tmp
EXPOSE 8001
ARG JAR_FILE=build/libs/CoreEngine-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} CoreEngine-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/CoreEngine-0.0.1-SNAPSHOT.jar"]
