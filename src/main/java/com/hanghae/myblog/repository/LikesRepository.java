package com.hanghae.myblog.repository;

import com.hanghae.myblog.entity.Likes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikesRepository extends JpaRepository<Likes,Long> {
}
