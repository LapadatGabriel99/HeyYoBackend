package com.chat.HeyYo.repository;

import com.chat.HeyYo.document.Complaint;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ComplaintRepository extends MongoRepository<Complaint, String> {
}
