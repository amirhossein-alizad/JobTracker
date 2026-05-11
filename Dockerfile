FROM eclipse-temurin:25-jre

WORKDIR /app

COPY target/jobtracker.jar jobtracker.jar

EXPOSE 8080
ENV APP_ENV=docker
ENTRYPOINT ["java", "-jar", "jobtracker.jar"]