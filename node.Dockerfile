FROM java:8
RUN mkdir /app
WORKDIR /app
COPY build/libs/BlockchainNodeService.jar BlockchainNodeService.jar
EXPOSE 8080
CMD java -jar BlockchainNodeService.jar