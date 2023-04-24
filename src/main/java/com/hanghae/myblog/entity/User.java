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

    private User(String username, String password) {
        this.username = username;
        this.password = password;
    }
    public static User of(String username,String password){
        return new User(username,password);
    }
}

