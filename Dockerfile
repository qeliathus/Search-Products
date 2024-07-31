# Use Maven image with JDK 17 to build the application
FROM maven:3.8.4-openjdk-17-slim AS build

# Copy the project files to the container
COPY . /app
WORKDIR /app

# Run Maven package command
RUN mvn package -DskipTests

# Use a minimal JRE image with JDK 17 to run the application
FROM openjdk:17-slim

# Copy the built JAR from the build stage
COPY --from=build /app/target/*.jar /app/app.jar

WORKDIR /app

# Expose the application port
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "app.jar"]