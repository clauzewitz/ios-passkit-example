package com.example.demo.prop;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "passbook.cert")
@Data
public class CertProperties {
    private String certPath;
    private String password;
    private String wwrcPath;
}
