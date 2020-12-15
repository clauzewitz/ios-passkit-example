package com.example.demo.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@Document(collection = "templates")
public class Template {
    @Id
    private ObjectId id;
    private String storeId;
    @Indexed(name = "idx_name", unique = true, direction = IndexDirection.ASCENDING)
    @NonNull
    private String name;
    private String desc;
    private String thumbnail;
    @NonNull
    private String logoPath;
    @NonNull
    private String logoRetinaPath;
    private String content;
    private List<String> tags;
    @Builder.Default
    @NonNull
    private long createDate = Instant.now().toEpochMilli();

    public String getId() {
        return id.toString();
    }

    @Override
    public String toString() {
        return "{" +
                "id: " + id +
                ", name: '" + name + '\'' +
                ", desc: '" + desc + '\'' +
                ", content: '" + content + '\'' +
                ", logPath: '" + logoPath + '\'' +
                '}';
    }
}
