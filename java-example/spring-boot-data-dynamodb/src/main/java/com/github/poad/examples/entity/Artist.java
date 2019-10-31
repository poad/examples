package com.github.poad.examples.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "artist")
public class Artist {
    private String name;
    private int age;

    public Artist() {
        // Default constructor is required by AWS DynamoDB SDK
    }

    public Artist(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @DynamoDBHashKey
    public String getName() {
        return name;
    }

    @DynamoDBAttribute
    public int getAge() {
        return age;
    }
}
