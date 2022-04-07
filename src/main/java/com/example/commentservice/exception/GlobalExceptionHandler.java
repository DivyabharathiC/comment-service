package com.example.commentservice.exception;

import com.example.commentservice.model.ApiError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({CommentNotFoundException.class})
    ResponseEntity globalExceptionHandler(Exception e, ServletWebRequest servletWebRequest) {
        ApiError apiError = new ApiError();

        apiError.setCode("404");
        apiError.setMessage(e.getMessage());
        apiError.setPath(servletWebRequest.getDescription(false));

        return new ResponseEntity(apiError, HttpStatus.NOT_FOUND);
    }
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {

        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();

        List<String> errors = fieldErrors.stream()
                .map(err -> err.getField() + " : " + err.getDefaultMessage())
                .collect(Collectors.toList());


        ApiError apiError = new ApiError();
        apiError.setCode("400");
        apiError.setMessage(String.valueOf(errors));
        apiError.setPath(request.getDescription(false));

        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }
}
