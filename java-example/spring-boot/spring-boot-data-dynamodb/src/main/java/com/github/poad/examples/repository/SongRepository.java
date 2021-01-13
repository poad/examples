package com.github.poad.examples.repository;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.github.poad.examples.entity.Song;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class SongRepository {
    private final DynamoDBMapper mapper;
    private final DynamoDBMapperConfig config;

    @Autowired
    public SongRepository(AmazonDynamoDB client, DynamoDBMapperConfig config) {
        this.mapper = new DynamoDBMapper(client, config);
        this.config = config;
    }

    public Song find(String artist, String title) {
        return mapper.load(Song.class, artist, title, config);
    }

    public List<Song> findByArtist(String artist) {
        Map<String, AttributeValue> eav = new HashMap<>();
        eav.put(":val1", new AttributeValue().withS(artist));
        DynamoDBQueryExpression<Song> queryExpression = new DynamoDBQueryExpression<Song>()
                .withKeyConditionExpression("artist = :val1").withExpressionAttributeValues(eav);
        return mapper.query(Song.class, queryExpression);
    }
}
