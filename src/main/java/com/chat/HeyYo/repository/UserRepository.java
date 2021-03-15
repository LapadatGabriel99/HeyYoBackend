package com.chat.HeyYo.repository;

import com.chat.HeyYo.document.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    public Optional<User> findByUsername(String userName);

    public Optional<User> findByEmail(String email);
}
