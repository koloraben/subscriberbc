FROM maven:3.8.4-openjdk-17-slim AS builder

WORKDIR /app

COPY ../pom.xml .

COPY ../src ./src

RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar

ENTRYPOINT ["java","-jar","-Dspring.profiles.active=prod","app.jar"]
