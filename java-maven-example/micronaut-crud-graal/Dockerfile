FROM mcr.microsoft.com/java/jdk:11-zulu-alpine as builder

COPY . /home/java/micronaut-crud-graal
WORKDIR /home/java/micronaut-crud-graal

RUN ./gradlew assemble --no-daemon

FROM poad/graalvm-ce:native-image as graalvm

COPY --from=builder /home/java/micronaut-crud-graal /home/java/micronaut-crud-graal

WORKDIR /home/java/micronaut-crud-graal

RUN native-image \
        --no-server \
        --static \
        --allow-incomplete-classpath \
        --initialize-at-run-time=io.micronaut.caffeine.cache.LocalCacheFactory \
        --no-fallback \
        -cp build/libs/micronaut-crud-graal-*-all.jar

FROM frolvlad/alpine-glibc
EXPOSE 8080
COPY --from=graalvm /home/java/micronaut-crud-graal .
ENTRYPOINT ["./micronaut-crud-graal"]
