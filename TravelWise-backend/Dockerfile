# Build stage
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copy the dependencies
COPY pom.xml .

# Download dependencies separately
# This step only runs if pom.xml changes. It downloads dependencies
RUN mvn dependency:go-offline

# Copy the source code. If this file changes, only the rest of
# the steps will re-run, reusing the cached layer
COPY src ./src

# Build the application
RUN mvn -DskipTests clean package

# Runtime stage
FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]

