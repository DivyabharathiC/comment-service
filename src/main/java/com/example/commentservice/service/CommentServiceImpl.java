package com.example.commentservice.service;

import com.example.commentservice.model.Comment;
import com.example.commentservice.repo.CommentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CommentServiceImpl implements CommentService{

    @Autowired
    CommentRepo commentRepo;


    @Override
    public Comment createComment(String postId, Comment comment) {
       comment.setPostId(postId);
       comment.setComment(comment.getComment());
       comment.setCommentedBy(comment.getCommentedBy());
       comment.setCreatedAt(LocalDateTime.now());
        return commentRepo.save(comment);

    }
}
