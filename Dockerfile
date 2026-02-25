# Stage 1: Build the application using Maven
FROM maven:3.9-eclipse-temurin-21 AS build

# Set the working directory
WORKDIR /app

# Copy the Maven project files
COPY pom.xml .

# Download dependencies (this layer is cached)
RUN mvn dependency:go-offline

# Copy the source code
COPY src ./src

# Build the application, skipping tests for a faster build
RUN mvn clean package -DskipTests

# Stage 2: Create the final, lightweight runtime image
FROM eclipse-temurin:21-jre-jammy

# Set the working directory
WORKDIR /app

# Copy the executable JAR from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the port the application runs on
EXPOSE 8080

# --- Environment Variables ---
# The application expects the following environment variables for the database connection.
# Pass them during the 'docker run' command.
# Example:
# docker run -p 8080:8080 \
#   -e DB_URL="jdbc:postgresql://..." \
#   -e DB_USERNAME="your_user" \
#   -e DB_PASSWORD="your_password" \
#   your-image-name
#
# ENV DB_URL=
# ENV DB_USERNAME=
# ENV DB_PASSWORD=

# The command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
