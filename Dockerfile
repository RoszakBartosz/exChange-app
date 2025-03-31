FROM eclipse-temurin:23-jdk
COPY target/exchange-app-0.0.1-SNAPSHOT.jar app.jar
CMD ["java", "-jar", "/app.jar"]