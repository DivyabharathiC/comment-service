package com.example.commentservice.dto;

import com.example.commentservice.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CommentDTO {

    @Id
    private String commentId;
    private String comment;
    private User commentedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer likeCounts;

}
