package com.example.commentservice.service;

import com.example.commentservice.model.Comment;
import org.springframework.stereotype.Service;

@Service
public interface CommentService {


    Comment createComment(String postId, Comment comment);
}
