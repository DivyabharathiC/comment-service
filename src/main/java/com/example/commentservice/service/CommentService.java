package com.example.commentservice.service;

import com.example.commentservice.dto.CommentDTO;
import com.example.commentservice.model.Comment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommentService {


    CommentDTO createComment(String postId, Comment comment);

    List<CommentDTO> getCommentsByPostId(String postId);

    Integer getCommentCount(String postId);

    String deleteByCommentId(String commentId);

    CommentDTO updateComment(Comment comment, String postId, String commentId);

    List<CommentDTO> getCommentsByCommentId(String postId, String commentId);
}
