FROM java:8
RUN mkdir /app
WORKDIR /app
COPY build/libs/DocumentService.jar DocumentService.jar
EXPOSE 8080
CMD java -jar DocumentService.jar --presentation-test