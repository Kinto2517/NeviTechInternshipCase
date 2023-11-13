FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY target/NeviTechInternshipCase-0.0.1-SNAPSHOT.jar /app/app.jar

EXPOSE 80801

CMD ["java", "-jar", "app.jar"]
