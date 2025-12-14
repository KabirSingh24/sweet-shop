# Stage 1: Build
FROM eclipse-temurin:17-jdk-jammy AS builder
WORKDIR /app
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline
COPY src ./src
RUN ./mvnw clean package -DskipTests

# Stage 2: Run
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
ENV PORT=8080
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]