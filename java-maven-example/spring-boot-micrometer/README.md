# Springboot with Prometheus and Grafana

## build

```$bash
./mvnw compile jib:dockerBuild
```

```$
docker-compose up -d
```

## Endpoints

### Application GUI
http://localhost:8089/

### Application monitoring(Prometheus metrics)
http://localhost:8089/actuator/prometheus

### Prometheus
http://localhost:9090/

### Grafana
http://localhost:3000/

## Tips
GrafanaからPrometheus をデータソースとして登録する場合は、URLの指定を
`http://172.17.0.1:9090/` とする必要がある。(Dockerコンテナー内からのホストアクセスのためのIPアドレス指定となる)

### 監視設定
HTTPリクエストのモニタリングは
`http_server_requests_seconds_count{exception="None",method="GET",outcome="SUCCESS",status="200",uri="/",}`
あたりが良い