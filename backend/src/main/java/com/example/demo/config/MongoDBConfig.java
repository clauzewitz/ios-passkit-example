package com.example.demo.config;

import com.example.demo.prop.MongoDBProperties;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.client.*;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.core.convert.*;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = {"com.example.demo.repository"})
@RequiredArgsConstructor
public class MongoDBConfig extends AbstractMongoClientConfiguration {

    @NonNull
    private MongoDBProperties mongoDBProperties;

    @Override
    protected String getDatabaseName() {
        return mongoDBProperties.getDatabase();
    }

    @Override
    protected boolean autoIndexCreation() {
        return mongoDBProperties.isAutoIndexCreation();
    }

    @Override
    @Bean
    public MongoClient mongoClient() {
        MongoCredential mongoCredential = MongoCredential.createCredential(mongoDBProperties.getUserName(), this.getDatabaseName(), mongoDBProperties.getPassword().toCharArray());
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder().credential(mongoCredential).build();
        return MongoClients.create(mongoClientSettings);
    }

    @Override
    public MongoDatabaseFactory mongoDbFactory() {
        MongoDatabaseFactory mongoDatabaseFactory = new SimpleMongoClientDatabaseFactory(this.mongoClient(), this.getDatabaseName());
        return mongoDatabaseFactory;
    }

    @Override
    public MappingMongoConverter mappingMongoConverter(MongoDatabaseFactory databaseFactory, MongoCustomConversions customConversions, MongoMappingContext mappingContext) {
        DbRefResolver dbRefResolver = new DefaultDbRefResolver(databaseFactory);
        MappingMongoConverter mappingMongoConverter = new MappingMongoConverter(dbRefResolver, mappingContext);
        mappingMongoConverter.setTypeMapper(new DefaultMongoTypeMapper(null));
        return mappingMongoConverter;
    }
}
