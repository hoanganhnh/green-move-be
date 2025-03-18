# Build stage
FROM maven:3.8.5-openjdk-17-slim AS build
WORKDIR /app

# Copy the project files
COPY pom.xml .
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Runtime stage
FROM openjdk:17-slim
WORKDIR /app

# Copy the built JAR from the build stage
COPY --from=build /app/target/*.jar /app/spring-boot-boilerplate.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","spring-boot-boilerplate.jar"]