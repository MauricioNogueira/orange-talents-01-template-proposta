FROM openjdk:11.0.7-jre-slim-buster

COPY target/*.jar proposta.jar

ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod", "proposta.jar"]
