package com.chat.HeyYo.repository;

import com.chat.HeyYo.document.ChatRoom;
import com.chat.HeyYo.document.Message;
import com.chat.HeyYo.exception.DataNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Field;
import java.util.Objects;
import java.util.Optional;

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

    public Optional<ChatRoom> findByRoomName(String roomName) {

        AggregationOperation match = Aggregation.match(Criteria.where("name").is(roomName));
        AggregationOperation unwindMessages = Aggregation.unwind("messages");
        AggregationOperation sort = Aggregation.sort(Sort.Direction.ASC, "messages.date");
        AggregationOperation group = Aggregation.group(
                "_id",
                      "name",
                      "nickname",
                      "owner",
                      "createdAt",
                      "members")
                .push("messages").as("messages");

        AggregationOperation project = Aggregation.project()
                .and("_id._id").as("_id")
                .and("_id.name").as("name")
                .and("_id.nickname").as("nickname")
                .and("_id.owner").as("owner")
                .and("_id.createdAt").as("createdAt")
                .and("messages").as("messages")
                .and("_id.members").as("members");

        Aggregation aggregation = Aggregation.newAggregation(
                match, unwindMessages , sort, group, project);

        ChatRoom room = mongoTemplate.aggregate(aggregation,
                mongoTemplate.getCollectionName(ChatRoom.class), ChatRoom.class).getUniqueMappedResult();

        return Optional.of(Objects.requireNonNull(room));
    }
}
