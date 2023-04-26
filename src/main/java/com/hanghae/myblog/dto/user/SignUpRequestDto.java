package com.hanghae.myblog.dto.user;

import com.hanghae.myblog.entity.User;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpRequestDto {
    @Pattern(regexp = "^[a-z0-9]{4,10}$",message = "아이디 형식이 맞지 않습니다.")
    private String username;
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{8,15}$",message = "비밀번호 형식이 맞지 않습니다.")
    private String password;
    private boolean admin=false;
    private String adminToken="";

    public static User toEntity(SignUpRequestDto signUpRequestDto){
        return User.builder().username(signUpRequestDto.getUsername())
                .password(signUpRequestDto.getPassword()).build();
    }
}
