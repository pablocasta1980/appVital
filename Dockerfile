#
# Build stage
#
FROM gradle:8.8-jdk17 AS build
WORKDIR /home/gradle/src
COPY --chown=gradle:gradle . .
RUN gradle clean bootJar --no-daemon

#
# Package stage
#
FROM openjdk:17-jdk-slim
ARG JAR_FILE=build/libs/*.jar
COPY --from=build /home/gradle/src/${JAR_FILE} app.jar

# Define puerto por defecto (puedes cambiarlo en tiempo de ejecución)
ENV PORT=8081
EXPOSE ${PORT}

ENTRYPOINT ["java", "-jar", "/app.jar"]
