FROM mcr.microsoft.com/java/jdk:8-zulu-alpine AS build

COPY . /root/app

WORKDIR /root/app

RUN ./gradlew -Dquarkus.profile=container quarkusBuild --uber-jar

FROM mcr.microsoft.com/java/jdk:8-zulu-alpine

RUN addgroup -g 101 -S java \
 && adduser -S -D -H -u 101 -G java -g java java

COPY --from=build /root/app/build/quarkus-mp-example-1.0.0-SNAPSHOT-runner.jar /home/java/quarkus-mp-example-runner.jar

ENTRYPOINT [ "java", "-jar", "/home/java/quarkus-mp-example-runner.jar" ]
