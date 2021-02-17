#### Stage 1: Build the application
FROM openjdk:15-ea-8-jdk-slim-buster as build

RUN apt-get update && apt-get install dos2unix
# Set the current working directory inside the image
# cd /app
WORKDIR /app

# Copy maven executable to the image
COPY mvnw .
COPY .mvn .mvn

# Copy the pom.xml file
COPY pom.xml .

# Build all the dependencies in preparation to go offline.
# This is a separate step so the dependencies will be cached unless
# the pom.xml file has changed.
# this has to be here for windows machines
RUN dos2unix /app/mvnw
RUN ./mvnw dependency:go-offline -B
# Copy the project source
COPY src src

# Package the application
# RUN ./mvnw package -DskipTests
RUN ./mvnw package
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

#### Stage 2: A minimal docker image with command to run the app
FROM openjdk:15-alpine as runner

ARG DEPENDENCY=/app/target/dependency

# Copy project dependencies from the build stage
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app

ENTRYPOINT ["java","-cp","app:app/lib/*","no.hvl.dat251.gr9.lopbackend.LopBackendApplication"]