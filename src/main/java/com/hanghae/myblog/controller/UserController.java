package com.hanghae.myblog.controller;

import com.hanghae.myblog.dto.ResponseDto;
import com.hanghae.myblog.dto.user.LoginRequestDto;
import com.hanghae.myblog.dto.user.SignUpRequestDto;
import com.hanghae.myblog.security.UserDetailsImpl;
import com.hanghae.myblog.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @PostMapping("/signup")
    public ResponseEntity<ResponseDto> signUp(@RequestBody @Validated SignUpRequestDto signUpRequestDto){
        return new ResponseEntity<>(userService.signUp(signUpRequestDto), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDto> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response){
        return new ResponseEntity<>(userService.login(loginRequestDto,response),HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<ResponseDto> logout(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return ResponseEntity.ok(userService.logout(userDetails.getUser()));
    }

    @GetMapping("/user-info")
    @ResponseBody
    public String getUserName(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return userDetails.getUsername();
    }
}
