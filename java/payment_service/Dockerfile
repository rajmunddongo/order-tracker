# Use a Maven image to build the application
FROM maven:3.8.4-openjdk-17 AS builder

# Set the working directory
WORKDIR /app

# Copy the POM file
COPY pom.xml .

# Fetch the dependencies
RUN mvn dependency:go-offline

# Copy the application source code
COPY src ./src

# Build the application
RUN mvn package

# Use the OpenJDK 17 JRE image as the base image for the final image
FROM openjdk:17-jre-alpine

# Set the working directory
WORKDIR /app

# Copy the JAR file from the builder stage
COPY --from=builder /app/target/*.jar app.jar

# Expose port 8082
EXPOSE 8082

# Command to run the application
CMD ["java", "-jar", "app.jar"]
