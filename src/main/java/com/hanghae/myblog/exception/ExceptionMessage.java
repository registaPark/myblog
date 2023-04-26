package com.hanghae.myblog.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ExceptionMessage {
    NO_USER(HttpStatus.BAD_REQUEST,"존재하지 않는 사용자입니다."),
    TOKEN_ERROR(HttpStatus.BAD_REQUEST,"토큰이 일치하지 않습니다."),
    DUPLICATED_USER(HttpStatus.BAD_REQUEST,"이미 존재하는 USERNAME"),
    NOT_MATCHING_PASSWORD(HttpStatus.BAD_REQUEST,"비밀번호가 일치하지 않습니다."),
    NO_ARTICLE(HttpStatus.BAD_REQUEST,"게시글이 존재하지 않습니다."),
    NO_AUTH(HttpStatus.BAD_REQUEST,"권한이 없습니다."),
    NO_COMMENT(HttpStatus.BAD_REQUEST,"댓글이 존재하지 않습니다.");

    private final HttpStatus status;
    private final String message;

    ExceptionMessage(HttpStatus status,String message) {
        this.status = status;
        this.message = message;
    }

}

