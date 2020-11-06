package com.example.demo.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(collection = "templates")
public class Template {
    @Id
    private ObjectId id;
    @Indexed(name = "idx_name", unique = true, direction = IndexDirection.ASCENDING)
    private String name;
    private String desc;
    private String content;
    @Builder.Default
    @NonNull
    private long createDate = System.currentTimeMillis();

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
                '}';
    }
}
