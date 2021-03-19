package com.github.poad.examples.entity;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

@DynamoDbBean
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

    @DynamoDbPartitionKey
    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    @DynamoDbSortKey
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
