package com.github.poad.examples.repository;

import com.github.poad.examples.entity.Artist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

@Repository
@Scope("singleton")
public class ArtistRepository {
    private final DynamoDbTable<Artist> mappedTable;

    @Autowired
    public ArtistRepository(DynamoDbEnhancedClient enhancedClient) {
        String env = System.getenv().getOrDefault("ENV", "local");
        String prefix = String.join("", "test-", env, "-");

        this.mappedTable = enhancedClient.table(String.join(prefix, "artist"), TableSchema.fromBean(Artist.class));
    }

    public Artist findByName(String name) {
        //Create a KEY object
        Key key = Key.builder()
                .partitionValue(name)
                .build();

        // Get the item by using the key
        return mappedTable.getItem(r->r.key(key));
    }
}
