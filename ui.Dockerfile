FROM java:8
RUN mkdir /app
WORKDIR /app
COPY build/libs/UIService.jar UIService.jar
EXPOSE 8080
CMD java -jar UIService.jar