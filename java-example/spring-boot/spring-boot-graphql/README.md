# spring-boot-graphql

## Run by docker-compose

```$sh
cd server && \
./gradlew -Penv=container bootBuildImage && \
cd ..
docker-compose up -d
```

## Run without docker-compose

```$sh
./gradlew -Penv=local bootRun
```
