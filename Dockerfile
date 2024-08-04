FROM openjdk:17-jdk-slim

WORKDIR /app

COPY . .

ARG AWS_ACCESS_KEY_ID
ARG AWS_SECRET_ACCESS_KEY
ARG AWS_SESSION_TOKEN

ENV AWS_ACCESS_KEY_ID=${AWS_ACCESS_KEY_ID}
ENV AWS_SECRET_ACCESS_KEY=${AWS_SECRET_ACCESS_KEY}
ENV AWS_SESSION_TOKEN=${AWS_SESSION_TOKEN}


RUN chmod +x ./gradlew

RUN ./gradlew build

COPY . .

EXPOSE 8080

CMD ["java", "-jar", "build/libs/fastfood-orquestrador-0.0.1-SNAPSHOT.jar"]