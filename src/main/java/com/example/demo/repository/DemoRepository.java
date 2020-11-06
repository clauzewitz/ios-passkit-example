package com.example.demo.repository;

import com.example.demo.entity.Template;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DemoRepository extends MongoRepository<Template, String> {
    public Template findByName(String name) throws Exception;
}
