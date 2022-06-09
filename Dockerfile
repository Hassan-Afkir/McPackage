FROM openjdk:8
VOLUME /tmp
ADD target/McPackage*.jar /app.jar
CMD ["java", "-jar", "/app.jar"]
EXPOSE 8080