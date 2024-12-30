FROM openjdk:21-jdk-slim

ENV APP_HOME=/usr/app/

WORKDIR $APP_HOME

COPY build/libs/*.jar application.jar

EXPOSE 8080

ENTRYPOINT java -jar application.jar\
            -XX:+HeapDumpOnOutOfMemoryError\
            --spring.config.location=file:///usr/app/application.yml
