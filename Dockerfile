FROM maven:3.9.6-amazoncorretto-17 AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
COPY impl ./impl
RUN mvn clean package -DskipTests

FROM amazoncorretto:17
WORKDIR /app
COPY --from=builder /app/impl/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]