package com.example.commentservice.controller;

import com.example.commentservice.dto.CommentDTO;
import com.example.commentservice.model.Comment;
import com.example.commentservice.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@CrossOrigin(value ="*")
@RequestMapping(path="/api/v1/post")
public class CommentController {
    private static Logger logger = LoggerFactory.getLogger(CommentController.class);
    @Autowired
    CommentService commentService;

    @PostMapping("/{postId}/comments")
    public ResponseEntity<CommentDTO> createComment(@PathVariable("postId") String postId,@RequestBody @Valid Comment comment) {
        logger.info("Starting of createComment request from Comment application");
        return  new ResponseEntity<CommentDTO>(commentService.createComment(postId, comment), HttpStatus.CREATED);
    }

    @GetMapping("/{postId}/comments")
    public ResponseEntity<List<CommentDTO>> getCommentsByPostId(@PathVariable("postId") String postId,
                                                                @RequestParam(value = "page", required = false) Integer page,
                                                                @RequestParam(value = "size", required = false) Integer size) {
        logger.info("Starting of getCommentsByPostId request from Comment application");
        return new ResponseEntity<List<CommentDTO>>(commentService.getCommentsByPostId(postId, page, size), HttpStatus.OK);
    }

    @GetMapping("/{postId}/comments/{commentId}")
    public ResponseEntity<List<CommentDTO>> getCommentsByCommentId(@PathVariable("postId") String postId, @PathVariable("commentId") String commentId) {
        logger.info("Starting of getCommentsByCommentId request from Comment application");
        return new ResponseEntity<List<CommentDTO>>(commentService.getCommentsByCommentId(postId,commentId), HttpStatus.OK);
    }

    @GetMapping("/count")
    public ResponseEntity<Integer> getCount(@PathVariable("postId") String postId) {
        logger.info("Starting of getCount request from Comment application");
        return new ResponseEntity<Integer>(commentService.getCommentCount(postId), HttpStatus.OK);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<CommentDTO> updateComment(@RequestBody Comment comment, @PathVariable("postId") String postId, @PathVariable("commentId") String commentId) {
        logger.info("Starting of updateComment request from Comment application");
        return new ResponseEntity<CommentDTO >(commentService.updateComment(comment, postId, commentId), HttpStatus.OK);
    }


    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> deleteByCommentId(@PathVariable("postId") String postId,@PathVariable("commentId") String commentId) {
        logger.info("Starting of deleteByCommentId request from Comment application");
        return new ResponseEntity<String>(commentService.deleteByCommentId(commentId), HttpStatus.OK);
    }

}
