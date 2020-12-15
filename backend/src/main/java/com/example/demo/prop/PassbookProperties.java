package com.example.demo.prop;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "passbook")
@Data
public class PassbookProperties {
    private StandardProperties standard;
    private CertProperties cert;
    private IconProperties icon;
    private LogoProperties logo;
    private StripProperties strip;
}
