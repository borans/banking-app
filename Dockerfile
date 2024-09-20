FROM openjdk:22 AS build

COPY pom.xml mvnw ./
COPY .mvn .mvn
RUN ./mvnw dependency:resolve

COPY src src
RUN ./mvnw package

FROM openjdk:22
WORKDIR banking-app
COPY --from=build target/*.jar banking-app.jar
ENTRYPOINT ["java", "-jar", "banking-app.jar"]