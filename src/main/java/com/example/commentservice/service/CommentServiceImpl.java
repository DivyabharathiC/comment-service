package com.example.commentservice.service;


import com.example.commentservice.Feign.LikeFeignClient;
import com.example.commentservice.Feign.UserFeignClient;
import com.example.commentservice.dto.CommentDTO;
import com.example.commentservice.exception.CommentNotFoundException;
import com.example.commentservice.model.Comment;
import com.example.commentservice.model.User;
import com.example.commentservice.repo.CommentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.commentservice.constant.Constant.CommentNotFound;
import static com.example.commentservice.constant.Constant.DeletedSuccess;

@Service
public class CommentServiceImpl implements CommentService{

    @Autowired
    CommentRepo commentRepo;

    @Autowired
     UserFeignClient userFeignClient;
    @Autowired
     LikeFeignClient likeFeignClient;

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
    public List<CommentDTO> getCommentsByPostId(String postId, Integer page, Integer size) {
        page = Optional.ofNullable(page).orElse(0);
        size = Optional.ofNullable(size).orElse(10);
        Pageable paging = PageRequest.of(page, size);
        Page<Comment> comments = commentRepo.findByPostId(postId,paging);
        if (comments.isEmpty()){
            throw  new CommentNotFoundException( CommentNotFound );
        }
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
    public List<CommentDTO> getCommentsByCommentId(String postId, String commentId) {
        List<Comment> comments = commentRepo.findByCommentId(commentId);
        if (comments.isEmpty()){
            throw  new CommentNotFoundException( CommentNotFound );
        }
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
        if(commentRepo.findById(commentId).isPresent()){
            commentRepo.deleteById(commentId);
            return DeletedSuccess;
        }
        else{
            throw new CommentNotFoundException(CommentNotFound);
        }
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
