package com.example.commentservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "Comment")
public class Comment {

    @Id
    private String commentId;
    @NotEmpty(message = "postId is required")
    private String postId;
    @NotEmpty(message = "comment is required")
    private String comment;
    @NotEmpty(message = "commentedBy is required")
    private String commentedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
