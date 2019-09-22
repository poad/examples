FROM poad/graalvm-ce:native-image as graalvm

COPY . /home/app/micronaut-crud-graal
WORKDIR /home/app/micronaut-crud-graal

RUN ./gradlew assemble --no-daemon

RUN native-image \
        --no-server \
        --static \
        --allow-incomplete-classpath \
        --initialize-at-run-time=io.micronaut.caffeine.cache.LocalCacheFactory \
        --no-fallback \
#        -J-Xmx3g \
        -cp build/libs/micronaut-crud-graal-*-all.jar

FROM frolvlad/alpine-glibc
EXPOSE 8080
COPY --from=graalvm /home/app/micronaut-crud-graal .
ENTRYPOINT ["./micronaut-crud-graal"]
