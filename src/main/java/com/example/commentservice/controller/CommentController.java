package com.example.commentservice.controller;

import com.example.commentservice.dto.CommentDTO;
import com.example.commentservice.model.Comment;
import com.example.commentservice.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(value ="*")
@RequestMapping(path="/api/v1/posts/{postId}")
public class CommentController {

    @Autowired
    CommentService commentService;

    @PostMapping("/comments")
    public ResponseEntity<CommentDTO> createComment(@PathVariable("postId") String postId,@RequestBody Comment comment) {
        return  new ResponseEntity<CommentDTO>(commentService.createComment(postId, comment), HttpStatus.CREATED);
    }

    @GetMapping("/comments")
    public ResponseEntity<List<CommentDTO>> getCommentsByPostId(@PathVariable("postId") String postId) {
        return new ResponseEntity<List<CommentDTO>>(commentService.getCommentsByPostId(postId), HttpStatus.ACCEPTED);
    }

    @GetMapping("/comments/count")
    public ResponseEntity<Integer> getCount(@PathVariable("postId") String postId) {
        return new ResponseEntity<Integer>(commentService.getCommentCount(postId), HttpStatus.ACCEPTED);
    }

    @PutMapping("/comments/{commentId}")
    public ResponseEntity<CommentDTO> updateComment(@RequestBody Comment comment, @PathVariable("postId") String postId, @PathVariable("commentId") String commentId) {
        return new ResponseEntity<CommentDTO >(commentService.updateComment(comment, postId, commentId), HttpStatus.ACCEPTED);
    }


    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<String> deleteByCommentId(@PathVariable("postId") String postId,@PathVariable("commentId") String commentId) {
        return new ResponseEntity<String>(commentService.deleteByCommentId(commentId), HttpStatus.ACCEPTED);
    }

}
