# Use the official OpenJDK image as base
FROM openjdk:17-slim

# Set working directory for the application
WORKDIR /app

# Copy the built JAR file of the application
COPY target/*.jar app.jar

# Expose the port the application runs on
EXPOSE 8080

# Start the application
ENTRYPOINT ["java", "-jar", "app.jar"]