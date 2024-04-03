FROM eclipse-temurin:21

COPY target/contability-2.0.1.jar app.jar

ENTRYPOINT ["java", "-Xmx2048M", "-jar", "/app.jar"]