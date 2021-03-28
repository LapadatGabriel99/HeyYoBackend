package com.chat.HeyYo.repository;

import com.chat.HeyYo.document.ChatRoom;
import com.chat.HeyYo.document.Message;
import com.chat.HeyYo.exception.DataNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

@Repository
public class ChatRoomRepositoryImpl {

    @Autowired
    private MongoTemplate mongoTemplate;

    public void updateMembersList(String id, String newMember) {

        var data = mongoTemplate.findById(id, ChatRoom.class);

        if (data == null) {

            throw new DataNotFoundException(ChatRoom.class.getName());
        }

        var members = data.getListOfMembers();
        members.add(newMember);

        var query = new Query();
        query.addCriteria(Criteria.where("id").is(id));

        var update = new Update();
        update.set("members", members);

        mongoTemplate.updateFirst(query, update, ChatRoom.class);
    }

    public void updateMessageList(String id, Message message) {

        var data = mongoTemplate.findById(id, ChatRoom.class);

        if (data == null) {

            throw new DataNotFoundException(ChatRoom.class.getName());
        }

        var messages = data.getListOfMessages();
        messages.add(message);

        var query = new Query();
        query.addCriteria(Criteria.where("id").is(id));

        var update = new Update();
        update.set("messages", messages);

        mongoTemplate.updateFirst(query, update, ChatRoom.class);
    }

    public void updateOwner(String id) {

        var data = mongoTemplate.findById(id, ChatRoom.class);

        if (data == null) {

            throw new DataNotFoundException(ChatRoom.class.getName());
        }

        var members = data.getListOfMembers();
        var newOwner = members.first();

        var query = new Query();
        query.addCriteria(Criteria.where("id").is(id));

        var update = new Update();
        update.set("owner", newOwner);

        mongoTemplate.updateFirst(query, update, ChatRoom.class);
    }

    public void updateRoomName(String id, String newName) {

        var query = new Query();
        query.addCriteria(Criteria.where("id").is(id));

        var update = new Update();
        update.set("name", newName);

        mongoTemplate.updateFirst(query, update, ChatRoom.class);
    }

    public void updateRoomNickname(String id, String newNickname) {

        var query = new Query();
        query.addCriteria(Criteria.where("id").is(id));

        var update = new Update();
        update.set("nickname", newNickname);

        mongoTemplate.updateFirst(query, update, ChatRoom.class);
    }
}
