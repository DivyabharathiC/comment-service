package com.example.commentservice.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor


public class FeignClientRequest {

        private List<Comment> comments;

    }
