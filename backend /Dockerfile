# Stage 1: Build
FROM maven:3.9-eclipse-temurin-21 AS build
LABEL authors="pedroolivsz"
WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline -q

COPY src ./src
RUN mvn package -DiskipTests -q

# Stage 2: Runtime
FROM eclipse-temurin:21-jre-alpine AS runtime
WORKDIR /app

RUN addgroup -S passgen && adduser -S passgen -G passgen
USER passgen

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]