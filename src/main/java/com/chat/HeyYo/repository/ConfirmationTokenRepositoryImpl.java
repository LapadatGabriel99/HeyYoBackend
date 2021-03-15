package com.chat.HeyYo.repository;

import com.chat.HeyYo.document.ConfirmationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public class ConfirmationTokenRepositoryImpl {

    @Autowired
    private MongoTemplate mongoTemplate;

    public void updateConfirmAt(String token, LocalDateTime confirmedAt) {

        var query = new Query();
        query.addCriteria(Criteria.where("token").is(token));

        var update = new Update();
        update.set("confirmedAt", confirmedAt);

        mongoTemplate.updateFirst(query, update, ConfirmationToken.class);
    }
}
