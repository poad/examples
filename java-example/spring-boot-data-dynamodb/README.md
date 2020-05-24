

```$sh
docker-compose up -d

aws dynamodb create-table --table-name test-local-artist --attribute-definitions '[{"AttributeName": "name", "AttributeType": "S"}]' --key-schema '[{"AttributeName": "name", "KeyType": "HASH"}]' --provisioned-throughput '{"ReadCapacityUnits": 1, "WriteCapacityUnits": 1}' --endpoint-url http://localhost:8000
aws dynamodb put-item --table-name test-local-artist --item '{"name": {"S": "平原綾香"}, "age": {"N": "35"}, "sex": {"S": "女性"}}' --endpoint-url http://localhost:8000

aws dynamodb create-table --table-name test-local-song --attribute-definitions '[{"AttributeName": "artist", "AttributeType": "S"},{"AttributeName": "title", "AttributeType": "S"}]' --key-schema '[{"AttributeName": "artist", "KeyType": "HASH"},{"AttributeName": "title", "KeyType": "RANGE"}]' --provisioned-throughput '{"ReadCapacityUnits": 1, "WriteCapacityUnits": 1}' --endpoint-url http://localhost:8000
aws dynamodb put-item --table-name test-local-song --item '{"artist": {"S": "平原綾香"}, "title": {"S": "Jupiter"}}' --endpoint-url http://localhost:8000
```