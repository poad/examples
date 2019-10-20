kuromoji
--------------------------

[kuromoji](https://github.com/atilika/kuromoji) based morphological analysis web api.

## How to run

```
./gradlew jibDockerBuild
docker run --rm --name kuromoji -it -p8080:8080 -d kuromoji-api-server:latest
```

### endpoints

- http://127.0.0.1:8080/kuromoji/ipadic
- http://127.0.0.1:8080/kuromoji/jumandic
- http://127.0.0.1:8080/kuromoji/naist-jdic
- http://127.0.0.1:8080/kuromoji/unidic
- http://127.0.0.1:8080/kuromoji/unidic-kanaaccent



