include deploy/.env

compose-up:
	docker-compose -f ./deploy/docker-compose.yml --env-file deploy/.env up --build

compose-down:
	docker-compose -f ./deploy/docker-compose.yml --env-file deploy/.env down --rmi all