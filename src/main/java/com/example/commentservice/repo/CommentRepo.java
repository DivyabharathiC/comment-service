package com.example.commentservice.repo;

import com.example.commentservice.model.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepo extends MongoRepository<Comment, String> {

    List<Comment> findByPostId(String postId);


    List<Comment> findByCommentId(String commentId);
}
