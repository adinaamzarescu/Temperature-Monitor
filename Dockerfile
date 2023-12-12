FROM gradle:jdk17 as build

WORKDIR /app
COPY build.gradle .
COPY settings.gradle .
COPY src ./src
COPY gradlew .

RUN gradle clean build -x test --no-daemon

FROM openjdk:17-jdk-slim

COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 6000
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
