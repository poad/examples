package com.github.poad.examples.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "song")
public class Song {
    private String artist;
    private String title;

    public Song() {
        // Default constructor is required by AWS DynamoDB SDK
    }

    public Song(String artist, String title) {
        this.artist = artist;
        this.title = title;
    }

    @DynamoDBHashKey(attributeName = "artist")
    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    @DynamoDBRangeKey(attributeName = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
