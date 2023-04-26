package com.hanghae.myblog.service;

import com.hanghae.myblog.dto.ResponseDto;
import com.hanghae.myblog.dto.TokenDto;
import com.hanghae.myblog.dto.user.LoginRequestDto;
import com.hanghae.myblog.dto.user.SignUpRequestDto;
import com.hanghae.myblog.entity.RefreshToken;
import com.hanghae.myblog.entity.User;
import com.hanghae.myblog.entity.UserRole;
import com.hanghae.myblog.exception.TokenNotValidException;
import com.hanghae.myblog.jwt.JwtUtil;
import com.hanghae.myblog.repository.RefreshTokenRepository;
import com.hanghae.myblog.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.hanghae.myblog.exception.ExceptionMessage.*;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenRepository refreshTokenRepository;
    @Value("${admin.secretKey}")
    private String adminToken;

    public ResponseDto signUp(SignUpRequestDto signUpRequestDto){
        if(userRepository.findByUsername(signUpRequestDto.getUsername()).isPresent()){
            throw new IllegalArgumentException(DUPLICATED_USER.getMessage());
        }
        if(signUpRequestDto.isAdmin() && !signUpRequestDto.getAdminToken().equals(adminToken)){
            throw new TokenNotValidException("관리자 토큰이 일치하지 않습니다.");
        }
        String encodePassword = passwordEncoder.encode(signUpRequestDto.getPassword()); // password encoding
        String username = signUpRequestDto.getUsername();
        User user;
        if(!signUpRequestDto.isAdmin()) user = createUser(username,encodePassword,UserRole.USER);
        else user = createUser(username,encodePassword,UserRole.ADMIN);
        userRepository.save(user);
        return new ResponseDto("회원가입 성공", HttpStatus.OK.value(),null);
    }

    public ResponseDto login(LoginRequestDto loginRequestDto, HttpServletResponse response){
        User user = userRepository.findByUsername(loginRequestDto.getUsername()).orElseThrow(() -> new IllegalArgumentException("없는 회원입니다."));
        if(!passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())){ // LoginRequestDto의 password 값과 encoding된 비밀번호 비교
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        TokenDto tokenDto = jwtUtil.createAllToken(user.getUsername(), user.getRole());
        Optional<RefreshToken> refreshToken = refreshTokenRepository.findByUsername(loginRequestDto.getUsername());
        if(refreshToken.isPresent()){
            RefreshToken savedRefreshToken = refreshToken.get();
            savedRefreshToken.updateToken(tokenDto.getRefreshToken().substring(7));
            refreshTokenRepository.flush();
        }else{
            RefreshToken newToken = new RefreshToken(tokenDto.getRefreshToken().substring(7), user.getUsername());
            refreshTokenRepository.save(newToken);
        }
        response.setHeader(JwtUtil.ACCESS_KEY,tokenDto.getAccessToken());
        response.setHeader(JwtUtil.REFRESH_KEY,tokenDto.getRefreshToken());
        return new ResponseDto("로그인 성공",HttpStatus.OK.value());
    }

    private User createUser(String username, String password, UserRole role) {
        return User.builder().username(username).password(password).role(role).build();
    }
}
