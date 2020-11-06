package com.example.demo.prop;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "passbook.icon")
@Data
public class IconProperties {
    private String name;
    private int width;
    private int height;
    private String retinaName;
    private int retinaWidth;
    private int retinaHeight;
}
