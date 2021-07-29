FROM java:8
RUN mkdir /app
WORKDIR /app
COPY build/libs/NodeService.jar NodeService.jar
EXPOSE 8080
CMD java -jar NodeService.jar