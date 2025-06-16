FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/lifecycle-service-1.0.0.jar app.jar

EXPOSE 8091

ENTRYPOINT ["java", "-jar", "app.jar"]