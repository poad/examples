#!/usr/bin/env sh

cd server &&
./gradlew bootBuildImage
cd ..

docker-compose build
