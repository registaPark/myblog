package com.hanghae.myblog.repository;

import com.hanghae.myblog.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;
import java.util.Optional;


public interface CommentRepository extends JpaRepository<Comment,Long> {
    @Query("select c from Comment c left join fetch c.replyList")
    List<Comment> findAll();
    @Query("select c from Comment c left join fetch c.replyList where c.id = :id")
    Optional<Comment> findById(@Param("id") Long id);
}
