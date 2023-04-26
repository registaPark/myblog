package com.hanghae.myblog.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanghae.myblog.entity.User;
import com.hanghae.myblog.exception.GlobalExceptionDto;
import com.hanghae.myblog.repository.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Component
@Slf4j
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String access_token = jwtUtil.resolveToken(request,jwtUtil.ACCESS_KEY);
        String refresh_token = jwtUtil.resolveToken(request,jwtUtil.REFRESH_KEY);

        if(access_token != null) {
            if(!jwtUtil.validateToken(access_token)){
                jwtExceptionHandler(response, "Token Error", HttpStatus.UNAUTHORIZED.value());
            } else if (refresh_token!=null && jwtUtil.refreshTokenValidation(refresh_token)) {
                String username  = jwtUtil.getUserInfoFromToken(refresh_token).getSubject();
                User user = userRepository.findByUsername(username).get();
                String newAccessToken = jwtUtil.createToken(username, "Access", user.getRole());
                jwtUtil.setHeaderAccessToken(response,newAccessToken);
                setAuthentication(username);
            }else if(refresh_token==null){
                jwtExceptionHandler(response,"액세스 토큰이 만료되었습니다.",HttpStatus.BAD_REQUEST.value());
            }else{
                jwtExceptionHandler(response,"리프레시 토큰이 만료되었습니다.",HttpStatus.BAD_REQUEST.value());
            }
            Claims info = jwtUtil.getUserInfoFromToken(access_token);
            setAuthentication(info.getSubject());
        }
        filterChain.doFilter(request,response);
    }

    public void setAuthentication(String username) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = jwtUtil.createAuthentication(username);
        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);
    }

    public void jwtExceptionHandler(HttpServletResponse response, String msg, int statusCode) {
        response.setStatus(statusCode);
        response.setContentType("application/json");
        try {
            String json = new ObjectMapper().writeValueAsString(new GlobalExceptionDto(msg,statusCode));
            response.getWriter().write(json);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

}
