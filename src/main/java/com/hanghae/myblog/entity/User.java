package com.hanghae.myblog.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.userdetails.User.UserBuilder;


@Entity(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    @Enumerated(EnumType.STRING)
    private UserRole role;
    @Builder
    private User(String username, String password, UserRole role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }
}

