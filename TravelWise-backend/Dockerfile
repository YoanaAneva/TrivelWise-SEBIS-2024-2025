# 1. COPY ONLY THE POM (Dependencies)
COPY pom.xml .

# 2. DOWNLOAD DEPENDENCIES SEPARATELY (Leverage cache here!)
# This step only runs if pom.xml changes. It downloads dependencies.
RUN mvn dependency:go-offline

# 3. COPY THE SOURCE CODE (The code that changes often)
# If this file changes, only the rest of the steps will re-run,
# reusing the cached layer from step 2.
COPY src ./src

# 4. BUILD THE APPLICATION
RUN mvn -DskipTests clean package

# Runtime stage
FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]

