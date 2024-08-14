package com.patika.recommendationservice.exception;


import com.patika.recommendationservice.dto.response.GenericResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GenerealExceptionHandler {

    @ExceptionHandler(RecommendationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public GenericResponse<Void> handleRecommendationException(RecommendationException e) {
        return GenericResponse.failed(e.getMessage());
    }
}
