package com.chat.HeyYo.repository;

import com.chat.HeyYo.document.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl {

    @Autowired
    private MongoTemplate mongoTemplate;

    public void enableUser(String email) {

        var query = new Query();
        query.addCriteria(Criteria.where("email").is(email));

        var update = new Update();
        update.set("enabled", true);

        mongoTemplate.updateFirst(query, update, User.class);
    }

    public void updatePassword(String username, String password) {

        var query = new Query();
        query.addCriteria(Criteria.where("username").is(username));

        var update = new Update();
        update.set("password", password);

        mongoTemplate.updateFirst(query, update, User.class);
    }

    public void lockUser(String username) {

        var query = new Query();
        query.addCriteria(Criteria.where("username").is(username));

        var update = new Update();
        update.set("locked", true);

        mongoTemplate.updateFirst(query, update, User.class);
    }
}
