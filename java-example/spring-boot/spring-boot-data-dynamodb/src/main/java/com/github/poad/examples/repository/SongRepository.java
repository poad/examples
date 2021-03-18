package com.github.poad.examples.repository;

import com.github.poad.examples.entity.Song;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.PageIterable;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class SongRepository {
    private final DynamoDbTable<Song> mappedTable;

    @Autowired
    public SongRepository(DynamoDbEnhancedClient enhancedClient) {
        String env = System.getenv().getOrDefault("ENV", "local");
        String prefix = String.join("", "test-", env, "-");

        this.mappedTable = enhancedClient.table(String.join(prefix, "artist"), TableSchema.fromBean(Song.class));
    }

    public Song find(String artist, String title) {
        //Create a KEY object
        Key key = Key.builder()
                .partitionValue(artist)
                .sortValue(title)
                .build();

        // Get the item by using the key
        return mappedTable.getItem(r->r.key(key));
    }

    public List<Song> findByArtist(String artist) {
        // Create a QueryConditional object that is used in the query operation
        QueryConditional queryConditional = QueryConditional
                .keyEqualTo(Key.builder().partitionValue(artist)
                        .build());
        PageIterable<Song> results = mappedTable.query(r -> r.queryConditional(queryConditional));
        return results.stream().flatMap(page -> page.items().stream()).collect(Collectors.toList());
    }
}
