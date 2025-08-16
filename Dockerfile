# Build Device Information System Project
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
#Copy the entire contents of the current working directory on my local machine into the working directory of the Docker image.
COPY . .
RUN mvn clean package -DskipTests

# Run stage
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
