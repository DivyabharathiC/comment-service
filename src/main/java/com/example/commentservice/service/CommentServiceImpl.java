package com.example.commentservice.service;

import com.example.commentservice.model.Comment;
import com.example.commentservice.model.FeignClientRequest;
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

    @Override
    public FeignClientRequest getCommentsByPostId(String postId) {
        FeignClientRequest feignClientRequest=new FeignClientRequest();
        feignClientRequest.setComments(commentRepo.findByPostId(postId));
        return feignClientRequest;
    }

    @Override
    public Integer getCount(String postId) {
            Integer count=this.commentRepo.findByPostId(postId).size();
            return count;
    }
}
