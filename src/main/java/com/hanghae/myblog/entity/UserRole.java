package com.hanghae.myblog.entity;

public enum UserRole {
    ADMIN(Authority.ADMIN),USER(Authority.USER);

    private final String authority;

    UserRole(String authority) {
        this.authority = authority;
    }

    public String getAuthority() {
        return this.authority;
    }

    public static class Authority{
        // USER , ADMIN 의 authority 구분
        public static final String USER = "ROLE_USER";
        public static final String ADMIN = "ROLE_ADMIN";
    }
}
