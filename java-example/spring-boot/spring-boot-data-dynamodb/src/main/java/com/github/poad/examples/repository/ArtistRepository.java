package com.github.poad.examples.repository;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.github.poad.examples.entity.Artist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

@Repository
@Scope("singleton")
public class ArtistRepository {
    private final DynamoDBMapper mapper;
    private final DynamoDBMapperConfig config;

    @Autowired
    public ArtistRepository(AmazonDynamoDB client, DynamoDBMapperConfig config) {
        this.mapper = new DynamoDBMapper(client, config);
        this.config = config;
    }

    public Artist findByName(String name) {
        return mapper.load(Artist.class, name, config);
    }
}
