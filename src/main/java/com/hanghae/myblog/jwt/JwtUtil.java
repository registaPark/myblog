package com.hanghae.myblog.jwt;

import com.hanghae.myblog.dto.TokenDto;
import com.hanghae.myblog.entity.RefreshToken;
import com.hanghae.myblog.entity.UserRole;
import com.hanghae.myblog.repository.RefreshTokenRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtUtil {
    public static final String ACCESS_KEY = "ACCESS_KEY";
    public static final String REFRESH_KEY = "REFRESH_KEY";
    public static final String AUTHORIZATION_KEY = "auth";
    public static final String BEARER_PREFIX = "Bearer ";
    private final UserDetailsService userDetailsService;
    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${jwt.secret.key}")
    private String secretKey;
    private Key key;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    /**
     *
     * @param request
     * @return String
     * request header에서 토큰을 가져와서 반환
     */
    public String resolveToken(HttpServletRequest request,String token){
        String tokenName = token.equals(ACCESS_KEY) ? ACCESS_KEY : REFRESH_KEY;
        String bearerToken = request.getHeader(tokenName);
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)){
            return bearerToken.substring(7);
        }
        return null;
    }

    public TokenDto createAllToken(String username , UserRole role){
        return new TokenDto(createToken(username,"Access",role),createToken(username,"Refresh",role));
    }


    public String createToken(String username, String type, UserRole role){
        Date date = new Date();
        Date expTime = type.equals("Access") ? Date.from(Instant.now().plus(3, ChronoUnit.DAYS)) : Date.from(Instant.now().plus(3,ChronoUnit.DAYS));
        return BEARER_PREFIX+
                Jwts.builder()
                        .setSubject(username).claim(AUTHORIZATION_KEY,role)
                        .setExpiration(expTime)
                        .setIssuedAt(date)
                        .signWith(key,signatureAlgorithm)
                        .compact();
    }

    /**
     *
     * @param token
     * @return boolean
     * 토큰 검증로직
     */
    public boolean validateToken(String token){
        try{
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        }catch (SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token, 만료된 JWT token 입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
        } catch (IllegalArgumentException e) {
            log.info("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
        }
        return false;
    }

    public boolean refreshTokenValidation(String token){
        if(!validateToken(token)) return false;
        Optional<RefreshToken> refreshToken = refreshTokenRepository.findByUsername(getUserInfoFromToken(token).getSubject());
        return refreshToken.isPresent() && token.equals(refreshToken.get().getRefreshToken());
    }

    /**
     *
     * @param token
     * @return Claims
     * 토큰에서 유저정보 가져오기
     */
    public Claims getUserInfoFromToken(String token){
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    public Authentication createAuthentication(String username) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
    }

    public void setHeaderAccessToken(HttpServletResponse response, String accessToken){
        response.setHeader(ACCESS_KEY,accessToken);
    }
}