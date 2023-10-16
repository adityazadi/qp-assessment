# Stage 1: Build the JAR file
FROM maven:3.8.5-amazoncorretto-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package

# Stage 2: Create a minimal JRE image
FROM amazoncorretto:17
WORKDIR /app
COPY --from=build /app/target/*.jar /app/app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
