package com.hanghae.myblog.exception;

public class TokenNotValidException extends RuntimeException{
    public TokenNotValidException() {
        super();
    }

    public TokenNotValidException(String message) {
        super(message);
    }

    public TokenNotValidException(String message, Throwable cause) {
        super(message, cause);
    }

    public TokenNotValidException(Throwable cause) {
        super(cause);
    }

    protected TokenNotValidException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
