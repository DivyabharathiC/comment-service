package com.example.commentservice.exception;

import com.example.commentservice.model.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.Arrays;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({CommentNotFoundException.class})
    ResponseEntity globalExceptionHandler(Exception e, ServletWebRequest servletWebRequest) {
        ApiError apiError = new ApiError();
        apiError.setTimeStamp(LocalDateTime.now());
        apiError.setStatus(HttpStatus.NOT_FOUND);
        apiError.setError(Arrays.asList(e.getMessage()));
        apiError.setPath(servletWebRequest.getDescription(false));
        return new ResponseEntity(apiError, HttpStatus.NOT_FOUND);
    }
}
