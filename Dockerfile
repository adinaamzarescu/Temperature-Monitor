# Stage 1: Build the application
FROM gradle:jdk17 as build
WORKDIR /app
COPY build.gradle settings.gradle gradlew /app/
COPY src /app/src
RUN gradle clean build -x test --no-daemon

# Stage 2: Setup the runtime container
FROM openjdk:17-jdk-slim
COPY --from=build /app/build/libs/*.jar /app/app.jar
EXPOSE 6000
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app/app.jar"]
