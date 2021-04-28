FROM java:8
RUN mkdir /app
WORKDIR /app
COPY build/libs/BlockchainNodeManagerService.jar BlockchainNodeManagerService.jar
EXPOSE 8080
CMD java -jar BlockchainNodeManagerService.jar