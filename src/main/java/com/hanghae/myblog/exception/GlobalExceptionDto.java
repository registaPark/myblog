package com.hanghae.myblog.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class GlobalExceptionDto {
    private final String message;
    private final int code;

}
