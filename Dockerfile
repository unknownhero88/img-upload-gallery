# Stage 1: Build
FROM maven:3.9-eclipse-temurin-17 AS build

WORKDIR /app

# Copy pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy source code and build
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Run
FROM eclipse-temurin:17-jre-jammy

WORKDIR /app

# Copy the jar from build stage
COPY --from=build /app/target/Img-Url-con.jar app.jar

# Copy webapp files
COPY --from=build /app/src/main/webapp ./src/main/webapp

# Expose port
EXPOSE 10000

# Set environment variable for port
ENV PORT=10000

# Run the application
CMD ["java", "-jar", "app.jar"]
