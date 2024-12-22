FROM openjdk:21-jdk-slim

ENV APP_HOME=/usr/app/

WORKDIR $APP_HOME

COPY build/libs/namoner-0.0.1-SNAPSHOT.jar application.jar

EXPOSE 8080

ENTRYPOINT java -jar application.jar --spring.config.location=file:///usr/app/application.properties