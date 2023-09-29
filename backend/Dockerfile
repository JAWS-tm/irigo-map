# Build stage
FROM maven:3.8.5-openjdk-17-slim AS build

WORKDIR /home/app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src
RUN mvn clean install

######################
# Package stage
FROM eclipse-temurin:17-jre-alpine
WORKDIR /home/app

COPY --from=build /home/app/target/irigo-map.jar .
EXPOSE 8080

ENTRYPOINT ["java","-jar","irigo-map.jar"]