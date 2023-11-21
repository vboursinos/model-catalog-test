# model-catalog
EvoML model catalog microservice.

This repository contains the source code for a Java multi-module Spring Boot Web-flux JPA Application. 
The application uses Java as the primary language, Spring Boot for creating robust and scalable 
services, JPA for database interaction and Mono/flux to support reactiveness.

## Prerequisites ##
* Java 17
* Maven
* Docker
* Docker Compose
* Postgres for local development and testing

## How to Build Locally ##
* Ensure you have Maven installed (mvn -version to check your version).
* Run the following command in the root directory of the project to build the application.
    ```
    mvn clean package
    ```
* The build artifacts will be stored in the target/ directory.

## How to Run Locally ##
* Ensure you have a Postgres running locally.
* Adjust the application configuration in model-catalog-rest module src/main/resources/application.properties to point to your local postgres instance.
* Run the application using the command from model-catalog-rest module root directory.
  ```
  java -jar target/model-catalog-rest.jar
  ```

## Infrastructure Dependencies ##
This application depends on:

* Postgres server for data persistence
* Liquibase for SQL schema migration

## Docker Build ## 
To build a Docker image for this application, follow the steps below:

* Make sure Docker is installed and running (docker --version to check your version).
  * Run the following command at the root of the repository.
    ```
    docker build -t data-catalog ./docker
    ```

## Running Docker Container ##
* Run the Docker container using the command 
    ```
    docker run -p 8081:8081 -t data-catalog
    ```

## Docker Compose ##
* The docker-compose.yml file in the root directory of the repository defines the services that make up the application.
* The docker-compose.yml file defines the following services:
  * model-catalog-rest: The Spring Boot application.
  * postgres: The Postgres database.
  * zipkin: The Zipkin server for distributed tracing.
* To start up the application with Docker Compose, run the following command in the root directory of the repository: 
```
docker-compose up
```

* To stop the application, run 
```
docker-compose down
```

## Profiles/Configuration ##
The application supports different profiles for local and remote environments. To switch between them, adjust the spring.profiles.active setting in model-catalog-rest src/main/resources/application.properties.

## SQL Migration ##
SQL schema migration is performed using Liquibase. The changesets are located in model-catalog-entity src/main/resources/config.liquibase/changelog. There are 13 changesets in total, 2 DDLs and 11 DMLs, and they are run in the order they are listed in db.changelog-master.xml.

## Liquibase Management ##
* Liquibase uses the changesets defined in model-catalog-entity src/main/resources/config.liquibase/master.xml to update the database schema.
* To update the schema, Liquibase runs all the changesets that have not been run yet, in the order they are listed in master.xml.
* To run liquibase updates manually, you can use the following command from the root directory of the project:
    ```
    mvn liquibase:update
    ```
* All the configuration properties regarding liquibase is in application.properties in model-catalog-rest module, starting with spring.liquibase
* This application is configured to automatically run Liquibase on startup, so there is no need to manually execute the above command during regular usage.

## Testing ##

This application is configured to use JUnit for unit tests.

Run tests using the following command:
```
mvn test
```
This will run all the tests in the application and provide a summarized report.

