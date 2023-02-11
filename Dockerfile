FROM eclipse-temurin:17-jre-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} wood.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/wood.jar"]
