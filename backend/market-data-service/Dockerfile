FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/market-data-service-1.0.0.jar app.jar

EXPOSE 8083

ENTRYPOINT ["java", "-jar", "app.jar"]