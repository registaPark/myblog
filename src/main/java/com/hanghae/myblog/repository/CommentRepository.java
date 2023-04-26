package com.hanghae.myblog.repository;

import com.hanghae.myblog.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CommentRepository extends JpaRepository<Comment,Long> {
}
