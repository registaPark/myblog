package com.hanghae.myblog.exception;

public enum ExceptionMessage {
    NO_USER("존재하지 않는 사용자입니다."),
    DUPLICATED_USER("이미 존재하는 USERNAME"),
    NOT_MATCHING_PASSWORD("비밀번호가 일치하지 않습니다."),
    NO_ARTICLE("게시글이 존재하지 않습니다."),
    NO_AUTH("권한이 없습니다."),
    NO_COMMENT("댓글이 존재하지 않습니다.");

    private final String message;

    ExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

