FROM openjdk:16-alpine
VOLUME /tmp
EXPOSE 8080
ADD build/libs/api-0.0.1-SNAPSHOT.jar api.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/api.jar"]