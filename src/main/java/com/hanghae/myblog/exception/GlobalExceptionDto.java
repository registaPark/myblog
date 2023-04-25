package com.hanghae.myblog.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class GlobalExceptionDto {
    private final String message;
    private final int code;

}
