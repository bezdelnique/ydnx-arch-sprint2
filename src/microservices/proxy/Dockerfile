# syntax=docker/dockerfile:1.6

# Build stage with Gradle and Java 21
FROM gradle:8.6-jdk21-alpine AS build
WORKDIR /app

# Copy Gradle files first (for better layer caching)
COPY build.gradle .
COPY settings.gradle .
#COPY gradle.properties .
COPY gradle ./gradle
COPY gradlew .

RUN chmod +x ./gradlew

# Download dependencies (cached unless build files change)
#RUN ./gradlew dependencies --no-daemon
RUN --mount=type=cache,target=/home/gradle/.gradle \
    ./gradlew dependencies --no-daemon

# Copy source code
COPY src ./src

# Build the application
#RUN ./gradlew clean build -x test --no-daemon
RUN --mount=type=cache,target=/home/gradle/.gradle \
    ./gradlew clean build -x test --no-daemon

# Runtime stage
FROM eclipse-temurin:21-jre
WORKDIR /app

# Create non-root user for security
RUN groupadd -r spring && useradd -r -g spring spring
USER spring

# Copy the built artifact
COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar"]

