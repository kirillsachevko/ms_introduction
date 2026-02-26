FROM eclipse-temurin:21-jdk-alpine AS build

WORKDIR /app

COPY mvnw ./
COPY .mvn .mvn
COPY pom.xml ./

RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline -B

COPY src ./src

RUN ./mvnw clean package -Dmaven.test.skip=true -B

FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

EXPOSE 8761

COPY --from=build /app/target/*.jar app.jar

CMD ["java", "-jar", "app.jar"]
