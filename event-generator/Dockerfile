FROM openjdk:8-jdk-alpine
VOLUME /tmp
EXPOSE 8080
COPY target/event-generator-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT exec java -jar /app.jar --debug