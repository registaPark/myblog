package com.hanghae.myblog.repository;

import com.hanghae.myblog.entity.Likes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikesRepository extends JpaRepository<Likes,Long> {
    Optional<Likes> findByUserName(String username);
}
