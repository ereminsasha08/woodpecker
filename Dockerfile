FROM eclipse-temurin:21-jre-alpine
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} woodpecker.jar
EXPOSE 9090
ENTRYPOINT ["java","-jar","/woodpecker.jar"]
