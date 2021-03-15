package com.chat.HeyYo.config;

import com.chat.HeyYo.document.ERole;
import com.chat.HeyYo.document.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
public class MongoDbConfig {

    @Autowired
    MongoTemplate mongoTemplate;

    @PostConstruct
    public void initializeIndexes() {

        mongoTemplate.indexOps("users").ensureIndex(new Index("email", Sort.Direction.ASC).unique());
        mongoTemplate.indexOps("users").ensureIndex(new Index("username", Sort.Direction.ASC).unique());
    }

}
