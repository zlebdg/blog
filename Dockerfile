FROM zlebdg/alpine-openjdk8-dbg:latest

COPY target/*.jar /app.jar
