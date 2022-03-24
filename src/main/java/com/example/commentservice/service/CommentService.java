package com.example.commentservice.service;

import com.example.commentservice.model.Comment;
import com.example.commentservice.model.FeignClientRequest;
import org.springframework.stereotype.Service;

@Service
public interface CommentService {


    Comment createComment(String postId, Comment comment);

    FeignClientRequest getCommentsByPostId(String postId);

    Integer getCommentCount(String postId);

    String deletebyCommentId(String commentId);

    Comment updateComment(Comment comment, String postId, String commentId);
}
