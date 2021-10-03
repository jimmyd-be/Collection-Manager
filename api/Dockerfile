#
# Build stage
#
FROM maven:3.6.0-jdk-11-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean install
RUN mkdir -p /home/app/target/dependency && (cd /home/app/target/dependency; jar -xf ../*.jar)
RUN java -Djarmode=layertools -jar /home/app/target/*.jar extract --destination /home/app/target/extracted


#
# Package stage
#
FROM eclipse-temurin:11.0.12_7-jre-focal
VOLUME /tmp
ARG EXTRACTED=/home/app/target/extracted
COPY --from=build ${EXTRACTED}/dependencies/ ./
COPY --from=build ${EXTRACTED}/spring-boot-loader/ ./
COPY --from=build ${EXTRACTED}/snapshot-dependencies/ ./
COPY --from=build ${EXTRACTED}/application/ ./
ENTRYPOINT ["java","-noverify","-XX:TieredStopAtLevel=1","-Dspring.main.lazy-initialization=true","org.springframework.boot.loader.JarLauncher"]