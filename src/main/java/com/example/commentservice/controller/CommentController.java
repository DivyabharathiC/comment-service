package com.example.commentservice.controller;

import com.example.commentservice.model.Comment;
import com.example.commentservice.model.FeignClientRequest;
import com.example.commentservice.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/posts")
public class CommentController {

    @Autowired
    CommentService commentService;

    @PostMapping("/{postId}/comments")
    public ResponseEntity<Comment> createComment(@PathVariable("postId") String postId,@RequestBody Comment comment) {
        return  new ResponseEntity<Comment>(commentService.createComment(postId, comment), HttpStatus.CREATED);
    }

    @GetMapping("/{postId}/comments")
    public ResponseEntity<FeignClientRequest> getCommentsByPostId(@PathVariable("postId") String postId) {
        return new ResponseEntity<FeignClientRequest>(commentService.getCommentsByPostId(postId), HttpStatus.ACCEPTED);
    }

    @GetMapping("/{postId}/comments/count")
    public ResponseEntity<Integer> getCount(@PathVariable("postId") String postId) {
        return new ResponseEntity<Integer>(commentService.getCount(postId), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{postId}/comments/{commentId}")
    public ResponseEntity<String> deletebyCommentId(@PathVariable("postId") String postId,@PathVariable("commentId") String commentId) {
        return new ResponseEntity<String>(commentService.deletebyCommentId(commentId), HttpStatus.ACCEPTED);
    }

}
