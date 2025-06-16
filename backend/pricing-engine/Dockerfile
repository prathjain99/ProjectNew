FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/pricing-engine-1.0.0.jar app.jar

EXPOSE 8092

ENTRYPOINT ["java", "-jar", "app.jar"]