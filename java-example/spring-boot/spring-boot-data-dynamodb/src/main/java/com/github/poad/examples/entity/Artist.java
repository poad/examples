package com.github.poad.examples.entity;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@DynamoDbBean
public class Artist {
    private String name;
    private int age;
    private String sex;

    public Artist() {
        // Default constructor is required by AWS DynamoDB SDK
    }

    public Artist(String name, int age, String sex) {
        this.name = name;
        this.age = age;
        this.sex = sex;
    }

    @DynamoDbPartitionKey
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

}
