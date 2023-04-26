package com.hanghae.myblog.repository;

import com.hanghae.myblog.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long> {
    Optional<RefreshToken> findByUsername(String username);
    void deleteByUsername(String username);
}
