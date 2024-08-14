package com.patika.bloghubservice.exception;

import com.patika.bloghubservice.dto.response.GenericResponse;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BlogHubException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public GenericResponse<Void> handleBlogHubException(BlogHubException blogHubException){
        return GenericResponse.failed(blogHubException.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public GenericResponse<HashMap<String, String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){

        HashMap<String, String > fieldErrorsMap = new HashMap<>();

        List<FieldError> fieldErrors = ex.getFieldErrors();

        fieldErrors.forEach(fieldError -> fieldErrorsMap.put(fieldError.getField(), fieldError.getDefaultMessage()));

       return new GenericResponse<>(ExceptionMessages.VALIDATION, HttpStatus.BAD_REQUEST, fieldErrorsMap);
    }

}
