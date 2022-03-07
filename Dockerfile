FROM openjdk:11-jre-slim

ARG JAR_FILE
ADD target/${JAR_FILE} service.jar
EXPOSE 8080
ENTRYPOINT ["java", \
            "-jar", \
            "service.jar"]