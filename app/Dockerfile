FROM amazoncorretto:21.0.4-alpine3.18

WORKDIR /app

RUN apk add --no-cache maven

COPY . /app

RUN mvn clean package

COPY target/*.jar app.jar

COPY docker-entrypoint.sh /app/docker-entrypoint.sh
RUN chmod +x /app/docker-entrypoint.sh

EXPOSE 8080

ENTRYPOINT ["/app/docker-entrypoint.sh"]
