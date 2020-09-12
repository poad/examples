FROM mcr.microsoft.com/java/jdk:11-zulu-alpine as builder
COPY . /app/micronaut-example
WORKDIR /app/micronaut-example
RUN ./mvnw package

FROM poad/graalvm-ce:native-image as graalvm

COPY --from=builder /app/micronaut-example/ /app/micronaut-example
WORKDIR /app/micronaut-example

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
