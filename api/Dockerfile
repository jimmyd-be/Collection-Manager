#
# Build stage
#
FROM gradle:jdk11-jammy AS build
COPY src /home/gradle/src
COPY build.gradle /home/gradle
COPY settings.gradle /home/gradle
RUN gradle build
RUN mkdir -p /home/gradle/build/libs/dependency && (cd /home/gradle/build/libs/dependency; jar -xf ../collection-manager-1.0.0.jar)
RUN mkdir /home/gradle/extracted
RUN java -Djarmode=layertools -jar /home/gradle/build/libs/collection-manager-1.0.0.jar extract --destination /home/gradle/extracted


#
# Package stage
#
FROM eclipse-temurin:11-jre-focal
VOLUME /tmp
ARG EXTRACTED=/home/gradle/extracted
COPY --from=build ${EXTRACTED}/dependencies/ ./
COPY --from=build ${EXTRACTED}/spring-boot-loader/ ./
COPY --from=build ${EXTRACTED}/snapshot-dependencies/ ./
COPY --from=build ${EXTRACTED}/application/ ./
ENTRYPOINT ["java","-noverify","-XX:TieredStopAtLevel=1","-Dspring.main.lazy-initialization=true","org.springframework.boot.loader.JarLauncher"]
