FROM oracle/graalvm-ce:19.2.0.1 as graalvm
RUN gu install native-image
COPY . /home/app/micronaut-server
WORKDIR /home/app/micronaut-server
RUN native-image --no-server --no-fallback \
    --initialize-at-build-time=io.netty.buffer.UnpooledByteBufAllocator \
    --initialize-at-build-time=org.h2.Driver \
    -cp build/libs/micronaut-server-*.jar

FROM frolvlad/alpine-glibc
EXPOSE 8080
COPY --from=graalvm /home/app/micronaut-server .
ENTRYPOINT ["./micronaut-server"]
