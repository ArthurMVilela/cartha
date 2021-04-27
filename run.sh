#!/bin/bash


function build() {
  echo "########################################"
  echo "Compilando: $1"
  export MAIN_CLASS_NAME=$1
  gradle shadowJar
  echo "Compilado com sucesso: $1"
  echo "########################################"
}

build "DocumentService"
build "BlockchainNodeService"
build "UIService"

docker-compose down
docker-compose up --build --force-recreate