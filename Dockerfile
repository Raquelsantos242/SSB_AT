FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

COPY target/infnet-0.0.1-SNAPSHOT.jar app.jar

CMD ["sh", "-c", "java -Dserver.port=$PORT -jar app.jar"]
