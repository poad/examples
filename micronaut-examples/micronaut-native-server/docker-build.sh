#!/bin/sh

docker build . -t micronaut-native-server && \
echo && \
echo && \
echo "To run the docker container execute:" && \
echo "    $ docker run -p 8080:8080 micronaut-native-server"
