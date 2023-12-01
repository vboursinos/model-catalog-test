# ─────────────────────────────── build stage ──────────────────────────────── #
FROM maven:3.9.5-eclipse-temurin-17 AS build
LABEL org.opencontainers.image.authors="Turintech.ai"

COPY . /home/app/
COPY ./setup/.m2 /root/.m2


RUN mvn -f /home/app/pom.xml -Dspring-boot.repackage.skip --batch-mode package

RUN mvn -f /home/app/pom.xml -DskipTests --offline --batch-mode package
# ─────────────────────────────── extracts the jar ──────────────────────────────── #
FROM azul/zulu-openjdk:17-jre-latest as builder
WORKDIR extracted

COPY --from=build ./home/app/model-catalog-rest/target/model-catalog-rest.jar model-catalog-rest.jar
RUN java -Djarmode=layertools -jar model-catalog-rest.jar extract

# ─────────────────────────────── runs the exploded jar ─────────────────────────── #
FROM azul/zulu-openjdk:17-jre-latest
WORKDIR application
COPY --from=builder extracted/dependencies/ ./
COPY --from=builder extracted/spring-boot-loader/ ./
COPY --from=builder extracted/snapshot-dependencies/ ./
COPY --from=builder extracted/application/ ./

RUN apt-get update && apt-get install -y \
curl

EXPOSE 8080

ENTRYPOINT ["java", "-XX:+UseSerialGC", "-XX:MaxRAM=1024m", "-XX:MaxMetaspaceSize=512m", "-Xmx8g", "-Xms256m", "-Xss512k", "org.springframework.boot.loader.JarLauncher"]
