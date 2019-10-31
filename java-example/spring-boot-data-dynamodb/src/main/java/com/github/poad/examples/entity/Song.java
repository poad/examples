package com.github.poad.examples.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "song")
public class Song {
    private String title;
    private String artist;

    public Song() {
        // Default constructor is required by AWS DynamoDB SDK
    }

    public Song(String title, String artist) {
        this.title = title;
        this.artist = artist;
    }

    @DynamoDBHashKey
    public String getTitle() {
        return title;
    }

    @DynamoDBAttribute
    public String getArtist() {
        return artist;
    }
}
