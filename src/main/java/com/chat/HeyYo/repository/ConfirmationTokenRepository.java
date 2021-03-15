package com.chat.HeyYo.repository;

import com.chat.HeyYo.document.ConfirmationToken;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface ConfirmationTokenRepository extends MongoRepository<ConfirmationToken, String> {

    Optional<ConfirmationToken> findByToken(String token);
}
