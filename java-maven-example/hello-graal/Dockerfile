FROM mcr.microsoft.com/java/jdk:11-zulu-alpine as builder

COPY . /home/app/hello-graal
WORKDIR /home/app/hello-graal
RUN ./gradlew assemble --no-daemon

FROM oracle/graalvm-ce:20.2.0-java11 as graalvm
RUN gu install native-image

COPY --from=builder /home/app/hello-graal /home/app/hello-graal
WORKDIR /home/app/hello-graal

RUN ls -al build/libs

RUN VERSION=$(./gradlew properties -q | grep "version:" | cut -d ' ' -f2) \
 && native-image --no-server \
    --no-fallback \
    --enable-https \
    --allow-incomplete-classpath \
    --initialize-at-build-time=io.netty.buffer.UnpooledByteBufAllocator \
    --initialize-at-build-time=org.h2.Driver \
    -H:+ReportExceptionStackTraces \
    -cp build/libs/hello-graal-${VERSION}.jar

FROM frolvlad/alpine-glibc
EXPOSE 8080
COPY --from=graalvm /home/app/hello-graal .
ENTRYPOINT ["./hello-graal"]
