package com.example.demo.prop;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "passbook.standard")
@Data
public class StandardProperties {
    private String teamIdentifier;
    private String passTypeIdentifier;
    private int formatVersion;
}