# ─────────────────────────────── build java stage ──────────────────────────────── #
FROM maven:3.9.5-eclipse-temurin-17 AS build-java
LABEL org.opencontainers.image.authors="Turintech.ai"

COPY model-catalog-migration-file-creator /app/model-catalog-migration-file-creator
COPY .env /app/.env
COPY pom.xml /app/pom.xml
COPY ./setup/.m2 /root/.m2


RUN mvn -f /app/model-catalog-migration-file-creator/pom.xml -Dspring-boot.repackage.skip --batch-mode package -DskipTests && rm -r /app/model-catalog-migration-file-creator/target

RUN mvn -f /app/model-catalog-migration-file-creator/pom.xml -DskipTests --offline --batch-mode package

# ─────────────────────────────── build python stage ──────────────────────────────── #
FROM python:3.8-slim as build-python

COPY model-catalog-migration-file-creator/model-catalog-py/setup/requirements.txt /app/requirements.txt
COPY setup/pip/pip.conf /etc/pip.conf
COPY setup/pip/.netrc /root/.netrc

WORKDIR /app
RUN pip install --no-cache-dir -r requirements.txt

# ─────────────────────────────── extracts the jar ──────────────────────────────── #
FROM eclipse-temurin:17-jre-alpine as builder
WORKDIR extracted

COPY --from=build-python /app /app
COPY --from=build-java ./app/model-catalog-migration-file-creator/target/model-catalog-migration-file-creator.jar model-catalog-migration-file-creator.jar
RUN java -Djarmode=layertools -jar model-catalog-migration-file-creator.jar extract

# ─────────────────────────────── runs the exploded jar ─────────────────────────── #
FROM eclipse-temurin:17-jre-alpine
WORKDIR application
COPY --from=builder extracted/dependencies/ ./
COPY --from=builder extracted/spring-boot-loader/ ./
COPY --from=builder extracted/snapshot-dependencies/ ./
COPY --from=builder extracted/application/ ./

EXPOSE 8080

ENV POSTGRES_HOST=model-catalog-postgres

ENTRYPOINT ["java", "-XX:+UseSerialGC", "-XX:MaxRAM=1024m", "-XX:MaxMetaspaceSize=512m", "-Xmx8g", "-Xms256m", "-Xss512k", "org.springframework.boot.loader.JarLauncher"]