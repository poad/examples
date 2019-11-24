FROM oracle/graalvm-ce:19.3.0 as graalvm
#FROM oracle/graalvm-ce:1.0.0-rc15 as graalvm

RUN gu install native-image

COPY . /home/app/hello-graal
WORKDIR /home/app/hello-graal

RUN ./gradlew assemble

RUN native-image --no-server -cp build/libs/hello-graal-*-all.jar

FROM frolvlad/alpine-glibc
EXPOSE 8080
COPY --from=graalvm /home/app/hello-graal .
ENTRYPOINT ["./hello-graal"]
