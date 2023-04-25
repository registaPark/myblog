package com.hanghae.myblog.repository;

import com.hanghae.myblog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    @Override
    Optional<User> findByUsername(String username);
}
