FROM oracle/graalvm-ce:19.3.1-java11 as graalvm

RUN gu install native-image

COPY . /home/app
WORKDIR /home/app
RUN ./gradlew assemble --no-daemon

RUN native-image --no-server -cp hello-world*.jar

FROM frolvlad/alpine-glibc
EXPOSE 8080
COPY --from=graalvm /home/app/hello-world/hello-world-bin .
ENTRYPOINT ["./hello-world-bin"]
