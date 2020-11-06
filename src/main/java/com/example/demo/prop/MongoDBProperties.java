package com.example.demo.prop;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "spring.data.mongodb")
@Data
public class MongoDBProperties {
    private String uri;
    private String database;
    private String userName;
    private String password;
    private boolean autoIndexCreation;
}
