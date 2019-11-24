FROM oracle/graalvm-ce:19.3.0-java11 as graalvm
RUN gu install native-image
COPY . /home/app/micronaut-native
WORKDIR /home/app/micronaut-native
RUN native-image --no-server --no-fallback \
    --initialize-at-build-time=com.mysql.cj.jdbc.Driver \
    --initialize-at-build-time=com.mysql.cj.jdbc.AbandonedConnectionCleanupThread \
    --initialize-at-build-time=com.sun.el.ExpressionFactoryImpl \
    --initialize-at-build-time=sun.reflect.misc.Trampoline \
    --initialize-at-run-time=sun.awt.dnd.SunDropTargetContextPeer$EventDispatcher \
    --allow-incomplete-classpath \
#    -H:ClassInitialization=com.mysql.cj.jdbc.AbandonedConnectionCleanupThread \
    -cp build/libs/micronaut-native-*.jar

FROM frolvlad/alpine-glibc
EXPOSE 8080
COPY --from=graalvm /home/app/micronaut-native .
ENTRYPOINT ["./micronaut-native"]
