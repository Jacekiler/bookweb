FROM amazoncorretto:17-alpine-jdk
WORKDIR /app
COPY target/notification-service-*.jar notification-service.jar
ENTRYPOINT ["java", "-jar", "notification-service.jar"]