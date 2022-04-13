package com.example.commentservice.repo;

import com.example.commentservice.model.Comment;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(MockitoExtension.class)
@DataMongoTest
class CommentRepoTest {

    @Autowired
    CommentRepo commentRepo;

    @BeforeEach
    void setUp() {
        Comment comment = createComment();
        commentRepo.save(comment);
    }

    @AfterEach
    void tearDown() {
        commentRepo.deleteAll();
    }

    @Test
    void findByPostId() {
        List<Comment> comment = commentRepo.findByPostId(createComment().getPostId());
        assertEquals("100", comment.get(0).getPostId());
    }

    @Test
    void testFindByPostId() {
        List<Comment> comment = commentRepo.findByPostId(createComment().getPostId(), Pageable.ofSize(99));
        assertEquals(Float.parseFloat("100"), 100);
    }

    @Test
    void findByCommentId() {
        List<Comment> comment = commentRepo.findByCommentId(createComment().getCommentId());
        assertEquals("1000", comment.get(0).getCommentId());
    }

    private Comment createComment() {
        Comment comment = new Comment();

        comment.setComment("comment1");
        comment.setCommentedBy("commentedbyrepo");
        comment.setCommentId("1000");
        comment.setPostId("100");

        return comment;
    }
}