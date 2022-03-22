package com.example.commentservice.repo;

import com.example.commentservice.model.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CommentRepo extends MongoRepository<Comment, String> {


}
