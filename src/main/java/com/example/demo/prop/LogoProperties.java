package com.example.demo.prop;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "passbook.logo")
@Data
public class LogoProperties {
    private String name;
    private int width;
    private int widewWidth;
    private int height;
    private String retinaName;
    private int retinaWidth;
    private int retinaWideWidth;
    private int retinaHeight;
}
