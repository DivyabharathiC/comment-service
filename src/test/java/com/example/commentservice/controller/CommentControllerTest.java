package com.example.commentservice.controller;

import com.example.commentservice.dto.CommentDTO;
import com.example.commentservice.enums.Gender;
import com.example.commentservice.model.Comment;
import com.example.commentservice.model.User;
import com.example.commentservice.service.CommentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@WebMvcTest(CommentController.class)
class CommentControllerTest {

    @MockBean
    CommentService commentService;

    @Autowired
    MockMvc mockMvc;

    public static String asJsonString(final Object object){
        try{
            return new ObjectMapper().writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void createComment() throws Exception {
        CommentDTO response = createCommentResponse();
        Comment comment = createNewCommentRequest();
        Mockito.when(commentService.createComment(comment.getPostId(),comment)).thenReturn(response);
        mockMvc.perform(post("/api/v1/posts/100/comments")
                        .content(asJsonString(comment))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }


    @Test
    void getCommentsByPostId() throws Exception {
        Comment request = createNewCommentRequest();
        List<CommentDTO> commentLists = getListOfCommentsByPostId();
        Mockito.when(commentService.getCommentsByPostId(request.getPostId(), 1,3)).thenReturn(commentLists);
        mockMvc.perform(get("/api/v1/posts/100/comments")
                        .content(asJsonString(commentLists))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }



    @Test
    void getCommentsByCommentId() throws Exception {
        Comment request = createNewCommentRequest();
        List<CommentDTO> commentLists = getListOfCommentsByCommentId();
        Mockito.when(commentService.getCommentsByCommentId(request.getPostId(), request.getCommentId())).thenReturn(commentLists);
        mockMvc.perform(get("/api/v1/posts/100/comments/1000")
                        .content(asJsonString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }



    @Test
    void getCount() throws Exception {
        Comment request = createNewCommentRequest();
        List<CommentDTO> commentLists = getListOfCommentsByPostId();
        Mockito.when(commentService.getCommentCount(request.getPostId())).thenReturn(commentLists.size());
        mockMvc.perform(get("/api/v1/posts/100/comments/count")
                        .content(asJsonString(commentLists))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void updateComment() throws Exception {
        Comment request = updateCommentRequest();
        CommentDTO commentDetails = createCommentResponse();
        Mockito.when(commentService.updateComment(request, request.getPostId(),request.getCommentId())).thenReturn(commentDetails);
        this.mockMvc.perform(put("/api/v1/posts/100/comments/1000")
                        .content(asJsonString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }



    @Test
    void deleteByCommentId() throws Exception {
        Comment request = createNewCommentRequest();
        Mockito.when(commentService.deleteByCommentId(request.getCommentId())).thenReturn(String.valueOf(true));
        mockMvc.perform(delete("/api/v1/posts/100/comments/1000")
                        .content(asJsonString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    private Comment createNewCommentRequest() {
        Comment comment = new Comment();
        comment.setCommentId("1000");
        comment.setPostId("100");
        comment.setComment("comment1");
        comment.setCommentedBy("9");

        return comment;
    }

    private CommentDTO createCommentResponse() {
        CommentDTO commentDTO = new CommentDTO();
        User response = createOneUserDataToPost();
        commentDTO.setComment("comment10");
        commentDTO.setCommentedBy(response);
        commentDTO.setCommentId("1000");
        commentDTO.setLikeCounts(6);
        return commentDTO;
    }

    private User createOneUserDataToPost() {
        User userDetails = new User();
        userDetails.setUserId("9");
        userDetails.setFirstName("John");
        userDetails.setMiddleName("Babu");
        userDetails.setLastName("Gyara");
        userDetails.setPhoneNumber("9700933932");
        userDetails.setDateOfBirth(new Date(1992, 9, 9));
        userDetails.setGender(Gender.MALE);
        userDetails.setEmployeeId("6969");
        userDetails.setBloodGroup("A+");
        userDetails.setEmail("gyarababu9@gmail.com");
        userDetails.setAddress("Hyderabad");
        return userDetails;
    }

    private List<CommentDTO> getListOfCommentsByPostId() {
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

    private List<CommentDTO> getListOfCommentsByCommentId() {
        List<CommentDTO> lists = new ArrayList<>();
        User response = createOneUserDataToPost();

        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setComment("comment1");
        commentDTO.setCommentedBy(response);
        commentDTO.setCommentId("1000");
        commentDTO.setLikeCounts(6);

        return lists;
    }

    private Comment updateCommentRequest() {
        Comment comment = new Comment();
        comment.setCommentId("1000");
        comment.setComment("comment for update");
        comment.setCommentedBy("9");
        comment.setPostId("100");
        return comment;
    }

}