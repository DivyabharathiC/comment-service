package com.example.commentservice.controller;

import com.example.commentservice.model.Comment;
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

}
