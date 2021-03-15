package com.chat.HeyYo.repository;

import com.chat.HeyYo.document.ERole;
import com.chat.HeyYo.document.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RoleRepository extends MongoRepository<Role, String> {

    Optional<Role> findByName(ERole name);
}
