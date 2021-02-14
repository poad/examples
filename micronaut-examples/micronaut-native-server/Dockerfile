FROM mcr.microsoft.com/java/jdk:11-zulu-alpine as builder

COPY . /home/app/micronaut-server
WORKDIR /home/app/micronaut-server
RUN ./gradlew assemble --no-daemon

FROM poad/graalvm-ce:native-image as graalvm

COPY --from=builder /home/app/micronaut-server /home/app/micronaut-server
WORKDIR /home/app/micronaut-server

RUN VERSION=$(./gradlew properties -q | grep "version:" | cut -d ' ' -f2) \
 && native-image --no-server \
    --no-fallback \
    --enable-https \
    --allow-incomplete-classpath \
    --initialize-at-build-time=io.netty.buffer.UnpooledByteBufAllocator \
    --initialize-at-build-time=org.h2.Driver \
    -H:+ReportExceptionStackTraces \
    -cp build/libs/micronaut-server-${VERSION}.jar

FROM frolvlad/alpine-glibc
EXPOSE 8080
COPY --from=graalvm /home/app/micronaut-server .
ENTRYPOINT ["./micronaut-server"]
