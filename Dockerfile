FROM maven:3.8.6

WORKDIR /app

COPY . /app

CMD java -jar target/API-Wipro-0.0.1-SNAPSHOT.jar