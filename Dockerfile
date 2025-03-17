# Use an official OpenJDK runtime as a parent image
FROM maven:3-eclipse-temurin-17

# Set the working directory in the container
WORKDIR /s-forms-manager-fork

# Copy the project files to the container
COPY . .

# Package the application
RUN mvn package -DskipTests

# Expose the port the application runs on
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "target/s-forms-manager-0.0.1.jar"]