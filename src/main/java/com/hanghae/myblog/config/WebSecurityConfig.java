package com.hanghae.myblog.config;

import com.hanghae.myblog.exception.CustomAccessDeniedHandler;
import com.hanghae.myblog.exception.CustomAuthenticationEntryPoint;
import com.hanghae.myblog.jwt.JwtAuthFilter;
import com.hanghae.myblog.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(securedEnabled = true) //@Secured 어노테이션 사용가능
public class WebSecurityConfig {
    private final JwtUtil jwtUtil;
    private final JwtAuthFilter jwtAuthFilter;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    @Bean // Password 해싱 기능
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return (web)-> web.ignoring()
                .requestMatchers(PathRequest.toH2Console())
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Bean // SecurityFilterChain 설정
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors().and() // CORS 정책
                .csrf().disable() // CSRF 비활성화
                .httpBasic().disable() // Basic 인증 비활성화
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and() //Session 기능 사용 X
                .authorizeHttpRequests().requestMatchers("/auth/**").permitAll() //해당 URL패턴 인증 X
                .anyRequest().authenticated()
                .and().addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        http.exceptionHandling().authenticationEntryPoint(customAuthenticationEntryPoint); // 없는 entryPoint 요청시 예외처리
        http.exceptionHandling().accessDeniedHandler(customAccessDeniedHandler); // 접근불가시 예외처리
        return http.build();
    }
}
