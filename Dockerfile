FROM maven:3.8.8-amazoncorretto-21-al2023 as build
WORKDIR /build
COPY . .
RUN mvn clean package -DskipTests

FROM amazoncorretto:21.0.5
WORKDIR /app
COPY --from=build ./build/target/*.jar ./app.jar

EXPOSE 8080

ENV DB_URL=''
ENV DB_USERNAME=''
ENV DB_PASSWORD=''
ENV GOOGLE_CLIENT_ID=''
ENV GOOGLE_CLIENT_SECRET=''
ENV TZ='America/Sao_Paulo'

ENTRYPOINT java -jar app.jar
