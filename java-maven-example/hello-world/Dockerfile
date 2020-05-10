FROM mcr.microsoft.com/java/jdk:11-zulu-alpine as builder
COPY . /home/app/hello-world
WORKDIR /home/app/hello-world
RUN ./gradlew assemble --no-daemon
 

FROM oracle/graalvm-ce:20.0.0-java11 as graalvm
RUN gu install native-image

COPY --from=builder /home/app/hello-world /home/app/hello-world

WORKDIR /home/app/hello-world

RUN ls -al build/libs

RUN VERSION=$(./gradlew properties -q | grep "version:" | cut -d ' ' -f2) \
 && native-image --no-server \
    --no-fallback \
    --enable-https \
    --allow-incomplete-classpath \
    --initialize-at-build-time=io.netty.buffer.UnpooledByteBufAllocator \
    --initialize-at-build-time=org.h2.Driver \
    -H:+ReportExceptionStackTraces \
    -cp build/libs/hello-world-${VERSION}.jar

RUN ls -al /home/app/hello-world/

FROM frolvlad/alpine-glibc
EXPOSE 8080
COPY --from=graalvm /home/app/hello-world/hello-world-bin .
ENTRYPOINT ["./hello-world-bin"]
