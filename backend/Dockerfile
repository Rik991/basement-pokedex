FROM eclipse-temurin:21-jdk

# Installa Maven
RUN apt-get update && \
    apt-get install -y maven && \
    apt-get clean

WORKDIR /app

COPY . .

EXPOSE 8080

CMD ["sh", "-c", "mvn clean package && java -jar target/backend-0.0.1-SNAPSHOT.jar"]