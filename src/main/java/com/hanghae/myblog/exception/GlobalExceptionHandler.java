package com.hanghae.myblog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    // GlobalException 처리
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<GlobalExceptionDto> signUpError(final MethodArgumentNotValidException e){
        // MethodArgumentNotValidException은 fieldError에서 가져와야 한다.
        return new ResponseEntity(new GlobalExceptionDto(e.getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<GlobalExceptionDto> illLegaError(final IllegalArgumentException e){
        return new ResponseEntity(new GlobalExceptionDto(e.getMessage(),HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
    }
}
