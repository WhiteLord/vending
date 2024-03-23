FROM maven:3.8.7-eclipse-temurin-19 AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean install -DskipTests

FROM openjdk:21-jdk
WORKDIR /app
COPY --from=builder /app/target/vending-1.0.0.jar  /app/vending-1.0.0.jar
EXPOSE 8765
CMD ["java", "-jar", "vending-1.0.0.jar"]
