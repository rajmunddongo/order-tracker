FROM openjdk:17-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ./target/ordertracker-1.0.0.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","/app.jar"]