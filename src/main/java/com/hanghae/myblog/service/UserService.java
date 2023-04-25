package com.hanghae.myblog.service;

import com.hanghae.myblog.dto.ResponseDto;
import com.hanghae.myblog.dto.user.LoginRequestDto;
import com.hanghae.myblog.dto.user.SignUpRequestDto;
import com.hanghae.myblog.entity.User;
import com.hanghae.myblog.entity.UserRole;
import com.hanghae.myblog.jwt.JwtUtil;
import com.hanghae.myblog.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    @Value("${admin.secretKey}")
    private String adminToken;

    public ResponseDto signUp(SignUpRequestDto signUpRequestDto){
        if(userRepository.findByUsername(signUpRequestDto.getUsername()).isPresent()){
            throw new IllegalArgumentException("이미 가입된 회원입니다.");
        }
        if(signUpRequestDto.isAdmin() && !signUpRequestDto.getAdminToken().equals(adminToken)){
            throw new IllegalArgumentException("관리자 토큰이 일치하지 않습니다.");
        }
        String encodePassword = passwordEncoder.encode(signUpRequestDto.getPassword()); // password encoding
        User user = User.of(signUpRequestDto.getUsername(), encodePassword);
        if(!signUpRequestDto.isAdmin()) user.setRole(UserRole.USER);
        else user.setRole(UserRole.ADMIN);
        userRepository.save(user);
        return new ResponseDto("회원가입 성공", HttpStatus.OK.value(),null);
    }

    public ResponseDto login(LoginRequestDto loginRequestDto, HttpServletResponse response){
        User user = userRepository.findByUsername(loginRequestDto.getUsername()).orElseThrow(() -> new IllegalArgumentException("없는 회원입니다."));
        if(!passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())){ // LoginRequestDto의 password 값과 encoding된 비밀번호 비교
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        String token = jwtUtil.createToken(user.getUsername(), user.getId());
        response.setHeader(JwtUtil.AUTHORIZATION_HEADER,token);
        return new ResponseDto("로그인 성공",HttpStatus.OK.value(),null);
    }
}
