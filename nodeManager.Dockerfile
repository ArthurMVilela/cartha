FROM java:8
RUN mkdir /app
WORKDIR /app
COPY build/libs/NodeManagerService.jar NodeManagerService.jar
EXPOSE 8080
CMD java -jar NodeManagerService.jar