package com.github.poad.examples.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.github.poad.examples.repository.ArtistRepository;
import com.github.poad.examples.repository.SongRepository;
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableDynamoDBRepositories(basePackageClasses = { ArtistRepository.class, SongRepository.class })
public class DynamoDBConfig {

    @Value("${dynamodb.endpoint}")
    private String endpoint;

    @Bean
    public AWSCredentials amazonAWSCredentials() {
        return DefaultAWSCredentialsProviderChain.getInstance().getCredentials();
    }

    @Bean
    public AmazonDynamoDB amazonDynamoDB() {
        return AmazonDynamoDBClientBuilder.standard().withCredentials(DefaultAWSCredentialsProviderChain.getInstance())
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endpoint, Regions.US_WEST_2.getName()))
                .build();
    }

    @Bean
    public DynamoDBMapperConfig dynamoDBMapperConfig(DynamoDBMapperConfig.ObjectTableNameResolver tableNameResolver) {
        // Create empty DynamoDBMapperConfig builder
        DynamoDBMapperConfig.Builder builder = new DynamoDBMapperConfig.Builder();
        // Inject the table name overrider bean
        builder.setObjectTableNameResolver(tableNameResolver);

        // Sadly this is a @deprecated method but new DynamoDBMapperConfig.Builder() is incomplete compared to DynamoDBMapperConfig.DEFAULT
        return builder.build();
    }

    @Bean
    public DynamoDBMapperConfig.ObjectTableNameResolver tableNameResolver() {
        String env = System.getenv().getOrDefault("ENV", "local");
        return (obj, config) ->
            String.join("-", "test", env, obj.getClass().getAnnotation(DynamoDBTable.class).tableName());
    }
}
