#!/usr/bin/env sh

cd server &&
./gradlew jibDockerBuild -Penv=compose
cd ..

docker-compose build
