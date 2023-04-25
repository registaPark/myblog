package com.hanghae.myblog.dto;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ResponseDto {
    private String message;
    private int statusCode;
    private Object data;

    public ResponseDto(String message, int statusCode, Object data) {
        this.message = message;
        this.statusCode = statusCode;
        this.data = data;
    }

    public ResponseDto(String message, int statusCode) {
        this.message = message;
        this.statusCode = statusCode;
        this.data = null;
    }

    public ResponseDto() {
        this.message = null;
        this.statusCode = HttpStatus.BAD_REQUEST.value();
        this.data=null;
    }
}
