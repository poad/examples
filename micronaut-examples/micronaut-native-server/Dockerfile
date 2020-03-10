FROM oracle/graalvm-ce:20.0.0-java11 as graalvm-build
RUN gu install native-image

COPY . /home/app/micronaut-server
WORKDIR /home/app/micronaut-server
RUN ./gradlew assemble

FROM oracle/graalvm-ce:20.0.0-java11 as graalvm
RUN gu install native-image
COPY --from=graalvm-build /home/app/micronaut-server /home/app/micronaut-server
WORKDIR /home/app/micronaut-server
RUN native-image --no-server \
    --no-fallback \
    --enable-https \
    --allow-incomplete-classpath \
    --initialize-at-build-time=io.netty.buffer.UnpooledByteBufAllocator \
    --initialize-at-build-time=org.h2.Driver \
    -H:+ReportExceptionStackTraces \
    -cp build/libs/micronaut-server-*.jar

FROM frolvlad/alpine-glibc
EXPOSE 8080
COPY --from=graalvm /home/app/micronaut-server .
ENTRYPOINT ["./micronaut-server"]
