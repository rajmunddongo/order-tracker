FROM openjdk:17-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ./target/file_upload_service-1.0.0.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]