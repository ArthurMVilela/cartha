build-ui-service:
	gradle shadowJar -PMainClassName="ui.service.UIService" -PBaseName="UIService"

build-document-service:
	gradle shadowJar -PMainClassName="DocumentService" -PBaseName="DocumentService"

build-authentication-service:
	gradle shadowJar -PMainClassName="authentication.service.AuthenticationService" -PBaseName="AuthenticationService"

build-node-service:
	gradle shadowJar -PMainClassName="BlockchainNodeService" -PBaseName="NodeService"

build-node-manager-service:
	gradle shadowJar -PMainClassName="BlockchainNodeManagerService" -PBaseName="NodeManagerService"

up-containers: build-ui-service build-document-service build-authentication-service build-node-service build-node-manager-service
	docker-compose up --build --force-recreate

down-containers:
	docker-compose down --remove-orphans

restart-ui-service: build-ui-service
	docker-compose up --build --force-recreate ui

restart-document-service: build-document-service
	docker-compose up --build --force-recreate document

restart-authentication-service: build-authentication-service
	docker-compose up --build --force-recreate authentication

restart-node-manager-service: build-node-manager-service
	docker-compose up --build --force-recreate node_manager