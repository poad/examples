FROM oracle/graalvm-ce:20.0.0-java8 as graalvm-build
RUN gu install native-image

COPY . /home/app/micronaut-native
WORKDIR /home/app/micronaut-native
RUN ./gradlew assemble

FROM oracle/graalvm-ce:20.0.0-java8 as graalvm
RUN gu install native-image
COPY --from=graalvm-build /home/app/micronaut-native /home/app/micronaut-native
WORKDIR /home/app/micronaut-native
RUN native-image --no-server \
    --no-fallback \
    -H:+ReportExceptionStackTraces \
    --initialize-at-build-time=com.mysql.cj.jdbc.Driver \
    --initialize-at-build-time=com.mysql.cj.jdbc.AbandonedConnectionCleanupThread \
    --initialize-at-build-time=com.sun.el.ExpressionFactoryImpl \
    --initialize-at-build-time=sun.reflect.misc.Trampoline \
    --initialize-at-run-time=sun.awt.dnd.SunDropTargetContextPeer$EventDispatcher \
    --allow-incomplete-classpath \
    -cp build/libs/micronaut-native-*.jar

FROM frolvlad/alpine-glibc
EXPOSE 8080
COPY --from=graalvm /home/app/micronaut-native .
ENTRYPOINT ["./micronaut-native"]
