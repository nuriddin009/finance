# 1. Use official OpenJDK 17 as base image
FROM eclipse-temurin:17-jdk-alpine as builder

# 2. Set working directory
WORKDIR /app

# 3. Copy pom.xml and download dependencies
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn
RUN ./mvnw dependency:go-offline -B

# 4. Copy source code and build the application
COPY src ./src
RUN ./mvnw clean package -DskipTests

# 5. Final image, minimize layers
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# 6. Copy built jar from builder stage
COPY --from=builder /app/target/*.jar app.jar

# 7. Expose application port
EXPOSE 8080

# 8. Entry point to run app
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
