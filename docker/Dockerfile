FROM openjdk:16-alpine
VOLUME /tmp
EXPOSE 8080
RUN mkdir -p /app/
RUN mkdir -p /app/logs/
ADD /build/libs/RestApi-0.0.1-SNAPSHOT.jar /app/RestApi.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app/RestApi.jar"]

