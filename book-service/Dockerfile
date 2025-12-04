FROM amazoncorretto:17-alpine-jdk
WORKDIR /app
COPY target/book-service-*.jar book-service.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "book-service.jar"]