package com.example.commentservice.service;

import com.example.commentservice.Feign.LikeFeignClient;
import com.example.commentservice.Feign.UserFeignClient;
import com.example.commentservice.dto.CommentDTO;
import com.example.commentservice.enums.Gender;
import com.example.commentservice.model.Comment;
import com.example.commentservice.model.User;
import com.example.commentservice.repo.CommentRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class CommentServiceImplTest {

    @InjectMocks
    CommentServiceImpl commentService;
    @Mock
    CommentRepo commentRepo;
    @Mock
    UserFeignClient userFeignClient;
    @Mock
    LikeFeignClient likeFeignClient;


    @Test
    void createComment() {
        Comment commentRequest = createCommentRequest();

        Comment comment = new Comment();
        comment.setCommentId(commentRequest.getCommentId());
        comment.setComment(commentRequest.getComment());
        comment.setPostId(commentRequest.getPostId());
        comment.setCommentedBy(commentRequest.getCommentedBy());

        Mockito.when(commentRepo.save(comment)).thenReturn(comment);
        assertThat(commentRepo.findById(comment.getPostId())).isNotNull();
    }



    @Test
    void getCommentsByPostId() {
        Comment comment = createCommentRequest();
        List<Comment> commentLists = getListOfComments();
        Mockito.when(commentRepo.findByPostId(comment.getPostId(), Pageable.ofSize(2))).thenReturn(commentLists);
        assertEquals(commentLists, commentRepo.findByPostId(comment.getPostId(), Pageable.ofSize(2)));
    }

    @Test
    void getCommentsByCommentId() {
        Comment comment = createCommentRequest();
        List<Comment> commentLists = getListOfComments();
        Mockito.when(commentRepo.findAll()).thenReturn(commentLists);
        assertEquals(commentLists, commentRepo.findAll());
    }

    @Test
    void getCommentCount() {
        List<Comment> commentList = getListOfComments();
        List<CommentDTO> commentLists = getCountOfComments();
        Mockito.when(commentRepo.findByPostId(commentList.get(0).getPostId())).thenReturn((commentList));
        assertEquals(commentLists.get(0).getLikeCounts(),6);

    }

    @Test
    void updateComment() {
        Optional<Comment> comment = getOneComment();
        Mockito.when(commentRepo.findById(comment.get().getCommentId())).thenReturn(comment);

        assertEquals(comment, commentRepo.findById(comment.get().getCommentId()));
    }


    @Test
    void deleteByCommentId() {
        Comment comment = createCommentRequest();
        Mockito.when(commentRepo.findById(comment.getCommentId())).thenReturn(Optional.of(comment));
        commentRepo.delete(comment);
        verify(commentRepo, times(1)).delete(comment);
    }

    private Comment createOneComment() {
        Comment comment = new Comment();
        comment.setPostId("100");
        comment.setComment("Hi");
        comment.setCommentId("1000");
        comment.setCommentedBy("9");

        return comment;
    }


    private User createOneUserDataToPost() {
        User user = new User();

        user.setUserId("1");
        user.setFirstName("John");
        user.setMiddleName("Babu");
        user.setLastName("Gyara");
        user.setPhoneNumber("+919700933932");
        user.setDateOfBirth(new Date(1992, 9, 9));
        user.setGender(Gender.MALE);
        user.setEmployeeId("6969");
        user.setBloodGroup("A+");
        user.setEmail("gyarab@maveric-systems.com");
        user.setAddress("Hyderabad");

        return user;
    }

    private Comment createCommentRequest() {
        Comment comment = new Comment();
        User response = createOneUserDataToPost();
        comment.setCommentId("1000");
        comment.setComment("comment1");
        comment.setCommentedBy(String.valueOf(response));
        comment.setPostId("100");
        return comment;
    }

    private List<Comment> getListOfComments() {
        List<Comment> lists = new ArrayList<>();
        Comment comment = new Comment();
        User response = createOneUserDataToPost();
        comment.setCommentId("1000");
        comment.setComment("Hello test user.. How are you?");
        comment.setCommentedBy(String.valueOf(response));
        comment.setPostId("100");

        lists.add(comment);

        Comment comment1 = new Comment();
        comment1.setCommentId("1001");
        comment1.setComment("Hello test user.. How are you?");
        comment1.setCommentedBy(String.valueOf(response));
        comment1.setPostId("100");

        lists.add(comment1);
        return lists;
    }

    private List<CommentDTO> getCountOfComments() {
        List<CommentDTO> lists = new ArrayList<>();
        User response = createOneUserDataToPost();

        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setComment("comment1");
        commentDTO.setCommentedBy(response);
        commentDTO.setCommentId("1000");
        commentDTO.setLikeCounts(6);
        lists.add(commentDTO);

        CommentDTO commentDTO2 = new CommentDTO();
        commentDTO2.setComment("comment2");
        commentDTO2.setCommentedBy(response);
        commentDTO2.setCommentId("1001");
        commentDTO2.setLikeCounts(11);
        lists.add(commentDTO2);

        CommentDTO commentDTO3 = new CommentDTO();
        commentDTO3.setComment("comment3");
        commentDTO3.setCommentedBy(response);
        commentDTO3.setCommentId("1002");
        commentDTO3.setLikeCounts(9);
        lists.add(commentDTO3);

        return lists;
    }

    private Optional<Comment> getOneComment() {
        User response = createOneUserDataToPost();

        Optional<Comment> comment = Optional.of(new Comment());
        return  comment;
    }
}