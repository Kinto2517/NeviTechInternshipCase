# Use the official OpenJDK base image with Alpine Linux
FROM openjdk:17-jdk-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file into the container at /app
COPY target/NeviTechInternshipCase-0.0.1-SNAPSHOT.jar /app/app.jar

# Expose the port that your Spring Boot application runs on
EXPOSE 8080

# Specify the command to run your application
CMD ["java", "-jar", "app.jar"]
