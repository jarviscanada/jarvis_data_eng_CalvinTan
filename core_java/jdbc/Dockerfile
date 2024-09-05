FROM openjdk:8-alpine
COPY target/jdbc-1.0-SNAPSHOT.jar /app/stock_quote.jar
ENTRYPOINT ["java", "-jar", "/app/stock_quote.jar"]