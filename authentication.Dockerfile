FROM java:8
RUN mkdir /app
WORKDIR /app
COPY build/libs/AuthenticationService.jar AuthenticationService.jar
EXPOSE 8080
CMD java -jar AuthenticationService.jar