# About this project

This project is result of training [Spring Boot Microservices Full Course](https://www.youtube.com/playlist?list=PLSVW22jAG8pBnhAdq9S8BpLnZ0_jVBj0c)

# Why run

Add `127.0.0.1	keycloak` on hosts file of you SO and run [docker-compose.yml](docker-compose.yml) on Docker Compose.

## Setup authentication

To authentication working is necessary generate the client secret on keycloak to `spring-cloud-client` and update the value on [collection.json](setup/postman/collection.json), before try to use the services endpoint.