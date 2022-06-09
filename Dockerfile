FROM openjdk:8
ADD target/McPackage-0.0.1-SNAPSHOT.jar McPackage-0.0.1-SNAPSHOT.jar
COPY target/${JAR_FILE} app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","McPackage-0.0.1-SNAPSHOT.jar"]