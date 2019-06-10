FROM alpine

COPY target/*.jar /app.jar

RUN apk --update add openjdk8-jre bash

EXPOSE 8080

CMD java -jar app.jar