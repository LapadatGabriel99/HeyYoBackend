package com.chat.HeyYo.repository;

import com.chat.HeyYo.document.ComplaintCategory;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ComplaintCategoryRepository extends MongoRepository<ComplaintCategory, String> {
}
