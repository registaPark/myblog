package com.hanghae.myblog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.hanghae.myblog.exception.ExceptionMessage.*;

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

    @ExceptionHandler(TokenNotValidException.class)
    public ResponseEntity<GlobalExceptionDto> notValidTokenError(final TokenNotValidException e){
        return new ResponseEntity<>(new GlobalExceptionDto(TOKEN_ERROR.getMessage(),TOKEN_ERROR.getStatus().value()),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoAuthException.class)
    public ResponseEntity<GlobalExceptionDto> noAuthError(final NoAuthException e){
        return new ResponseEntity<>(new GlobalExceptionDto(NO_AUTH.getMessage(),NO_AUTH.getStatus().value()),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoArticleException.class)
    public ResponseEntity<GlobalExceptionDto> noArticleError(final NoArticleException e){
        return new ResponseEntity<>(new GlobalExceptionDto(NO_ARTICLE.getMessage(), NO_ARTICLE.getStatus().value()),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoCommentException.class)
    public ResponseEntity<GlobalExceptionDto> noCommentError(final NoCommentException e){
        return new ResponseEntity<>(new GlobalExceptionDto(NO_COMMENT.getMessage(), NO_COMMENT.getStatus().value()),HttpStatus.BAD_REQUEST);
    }

}
