package com.example.commentservice.service;


import com.example.commentservice.Feign.LikeFeignClient;
import com.example.commentservice.Feign.UserFeignClient;
import com.example.commentservice.dto.CommentDTO;
import com.example.commentservice.model.Comment;
import com.example.commentservice.model.User;
import com.example.commentservice.repo.CommentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService{

    @Autowired
    CommentRepo commentRepo;

    @Autowired
    private UserFeignClient userFeignClient;
    @Autowired
    private LikeFeignClient likeFeignClient;

    @Override
    public CommentDTO createComment(String postId, Comment comment) {
        comment.setPostId(postId);
        comment.setCreatedAt(LocalDateTime.now());
        comment.setUpdatedAt(LocalDateTime.now());
        commentRepo.save(comment);

        CommentDTO commentDTO=new CommentDTO(comment.getCommentId(), comment.getComment(),
                userFeignClient.getUser(comment.getCommentedBy()),
                comment.getCreatedAt(),comment.getUpdatedAt(),
                likeFeignClient.getCount(comment.getCommentId()));

        return commentDTO;

    }

    @Override
    public List<CommentDTO> getCommentsByPostId(String postId) {
        List<Comment> comments = commentRepo.findAll();
        List<CommentDTO> commentDTOS = new ArrayList<>();

        for (Comment comment : comments) {
            User user = userFeignClient.getUser(comment.getCommentedBy());
            Integer likeCount = likeFeignClient.getCount(comment.getCommentId());

            commentDTOS.add(new CommentDTO(comment.getCommentId(), comment.getComment(),
                    user,
                    comment.getCreatedAt(), comment.getUpdatedAt(),
                    likeCount));
        }
        return commentDTOS;
    }

    @Override
    public Integer getCommentCount(String postId) {
            Integer count=commentRepo.findByPostId(postId).size();
            return count;
    }

    @Override
    public String deleteByCommentId(String commentId) {
       commentRepo.deleteById(commentId);
        return "Deleted CommentID from database for the commendId=" + commentId ;
    }

    @Override
    public CommentDTO updateComment(Comment comment, String postId, String commentId) {

        comment.setCommentId(commentId);
        comment.setCreatedAt(commentRepo.findById(commentId).get().getCreatedAt());
        comment.setUpdatedAt(LocalDateTime.now());
        comment.setPostId(postId);
        commentRepo.save(comment);

        CommentDTO commentDTO= new CommentDTO(comment.getCommentId(),comment.getComment(),
                userFeignClient.getUser(comment.getCommentedBy()),comment.getCreatedAt(),comment.getUpdatedAt(),
                likeFeignClient.getCount(comment.getCommentId()));

        return commentDTO;
    }

}
