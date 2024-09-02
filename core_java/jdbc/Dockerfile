FROM openjdk:8-alpine
COPY target/*.jar /app/jdbc-1.0-SNAPSHOT.jar.jar
ENTRYPOINT ["java", "-jar", "/app/stock_quote.jar"]