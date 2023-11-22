# model-catalog
EvoML model catalog microservice.

## Overview ##
This repository contains the source code for a Java multi-module Spring Boot Web-flux JPA Application. 
The application uses Java as the primary language, Spring Boot for creating robust and scalable 
services, JPA for database interaction and Mono/flux to support reactiveness.

In this repository, we employ a custom layered architecture to organize and structure our software system. 
This design choice helps to manage the complexity of our code and allows components to be segregated based on 
responsibilities. It's highly beneficial for increasing modularity, which leads to simpler maintenance and scalability.

1. model-catalog-api
  
2. model-catalog-dto

   DTO stands for Data Transfer Object. The model-catalog-dto layer includes classes that carry data between 
different layers inside the application. These DTOs are used to aggregate values.

3. model-catalog-dto-entity-mapper

   This layer is in charge of mapping DTO objects to Entity objects and vice versa. It helps to separate concerns, 
as the persistence logic model (entity) and the data transfer model (DTO) can evolve independently.

4. model-catalog-entity

   The model-catalog-entity layer represents the 'Entity' in our application. An entity is a business object that holds data. 
It is equivalent to a table in the database. All persistence logic and database interactions are managed in this layer.

5. model-catalog-facade

   The model-catalog-facade layer is used to streamline and consolidate complex operations, providing a simplified 
interface to clients. It wraps interactions with other layers, and orchestrates calls to multiple services to 
perform a single operation.

6. model-catalog-repository

   The model-catalog-repository layer is responsible for handling all database operations. It provides methods to 
query, create, update and delete entities. These repositories act as a bridge between the model-catalog-entity 
and model-catalog-service layers.

7. model-catalog-rest

   This is the outermost layer that interacts with the clients. The model-catalog-rest layer offers endpoints for 
external applications or services. It processes the incoming HTTP requests and corresponding responses which 
include converting the received data from TO to DTO objects and the reverse operation once the response is prepared.

8. model-catalog-service

   The model-catalog-service layer is tasked with handling business logic. It communicates with the
model-catalog-repository layer to persist or retrieve data. It also uses callables to execute tasks in parallel when it 
communicates with model-catalog-repository.

9. model-catalog-to

   TO stands for Transfer Object. This layer is very similar to the DTO layer, with the difference being that TOs are 
typically used for sending data across different services, outside the application.

10. model-catalog-to-dto-mapper

    This layer, much like the model-catalog-dto-entity-mapper, is responsible for converting TO to DTO, and vice versa. 
These mappers ensure a clean separation of concerns and promote code reusability.



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
The application supports different profiles for dev and prod environments. To switch between them, adjust the spring.profiles.active setting in model-catalog-rest src/main/resources/application.properties.

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

## Formatting ##

This application is configured to use the Google Java Style Guide for formatting.

* Run formatting using the following command:
  ```
  mvn com.coveo:fmt-maven-plugin:format
  ```

* To check if the code is formatted correctly, run the following command:
  ```
  mvn com.coveo:fmt-maven-plugin:check
  ```