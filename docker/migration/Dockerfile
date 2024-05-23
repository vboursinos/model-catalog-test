# ─────────────────────────────── build java stage ──────────────────────────────── #
FROM maven:3.9.5-eclipse-temurin-17 AS build-java
LABEL org.opencontainers.image.authors="Turintech.ai"

COPY ../../ /app/
COPY ../../.env /app/.env
COPY ../../pom.xml /app/pom.xml
COPY ../../setup/.m2 /root/.m2


RUN mvn -f /app/pom.xml -Dspring-boot.repackage.skip --batch-mode package -DskipTests && rm -r /app/target

RUN mvn -f /app/pom.xml -DskipTests --offline --batch-mode package

# ─────────────────────────────── extracts the jar ──────────────────────────────── #
FROM eclipse-temurin:17-jre-alpine as builder
WORKDIR extracted

COPY --from=build-java ./app/model-catalog-migration-file-creator/target/model-catalog-migration-file-creator.jar model-catalog-migration-file-creator.jar
RUN java -Djarmode=layertools -jar model-catalog-migration-file-creator.jar extract

# ─────────────────────────────── runs the exploded jar ─────────────────────────── #
FROM azul/zulu-openjdk:17-jre-latest

WORKDIR application
COPY --from=builder extracted/dependencies/ ./
COPY --from=builder extracted/spring-boot-loader/ ./
COPY --from=builder extracted/snapshot-dependencies/ ./
COPY --from=builder extracted/application/ ./
COPY --from=build-java ./app/model-catalog-migration-file-creator/model-catalog-py model-catalog-migration-file-creator/model-catalog-py

COPY ../../setup/pip/pip.conf /etc/pip.conf
COPY ../../setup/pip/.netrc /root/.netrc
COPY ../../model-catalog-migration-file-creator/static model-catalog-migration-file-creator/static
COPY ../../model-catalog-entity/src/main/resources/config/liquibase/changelog model-catalog-entity/src/main/resources/config/liquibase/changelog

RUN apt-get update && apt-get install -y python3 python3-pip
RUN python3 -m pip install -U pip setuptools

RUN python3 -m pip install --no-cache-dir -r model-catalog-migration-file-creator/model-catalog-py/setup/requirements.txt

EXPOSE 8080

ENV POSTGRES_HOST=model-catalog-postgres

ENTRYPOINT ["java", "-XX:+UseSerialGC", "-XX:MaxRAM=1024m", "-XX:MaxMetaspaceSize=512m", "-Xmx8g", "-Xms256m", "-Xss512k", "org.springframework.boot.loader.JarLauncher"]