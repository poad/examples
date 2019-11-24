FROM maven:3.6.0-jdk-8  as builder
COPY . /app/micronaut-example
WORKDIR /app/micronaut-example
RUN mvn package

FROM oracle/graalvm-ce:19.3.0 as graalvm
RUN gu install native-image
COPY --from=builder /app/micronaut-example/ /app/micronaut-example
WORKDIR /app/micronaut-example
RUN java -cp target/libs/micronaut-example-0.1.0-SNAPSHOT-all.jar \
            io.micronaut.graal.reflect.GraalClassLoadingAnalyzer \
            reflect.json
RUN ls -l
RUN native-image --no-server \
                 --class-path /app/micronaut-example/build/libs/*-all.jar \
    			 -H:ReflectionConfigurationFiles=/app/micronaut-example/reflect.json \
    			 -H:EnableURLProtocols=http \
    			 -H:IncludeResources='logback.xml|application.yml|META-INF/services/*.*' \
    			 -H:+ReportUnsupportedElementsAtRuntime \
    			 -H:+AllowVMInspection \
    			 --rerun-class-initialization-at-runtime='sun.security.jca.JCAUtil$CachedSecureRandomHolder',javax.net.ssl.SSLContext \
    			 --delay-class-initialization-to-runtime=io.netty.handler.codec.http.HttpObjectEncoder,io.netty.handler.codec.http.websocketx.WebSocket00FrameEncoder,io.netty.handler.ssl.util.ThreadLocalInsecureRandom \
    			 -H:-UseServiceLoaderFeature \
    			 -H:Name=micronaut-example \
    			 -H:Class=com.github.poad.examples.java.webapps.Application


FROM frolvlad/alpine-glibc
EXPOSE 8089
COPY --from=graalvm /app/micronaut-example/micronaut-example .
ENTRYPOINT ["./micronaut-example"]
