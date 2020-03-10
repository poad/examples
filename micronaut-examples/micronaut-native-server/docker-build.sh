#!/bin/sh

./gradlew assemble && \
docker build . -t micronaut-native && \
echo && \
echo && \
echo "To run the docker container execute:" && \
echo "    $ docker run -p 8080:8080 micronaut-native"
