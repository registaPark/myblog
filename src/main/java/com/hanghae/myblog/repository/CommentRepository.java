package com.hanghae.myblog.repository;

import com.hanghae.myblog.entity.Article;
import com.hanghae.myblog.entity.Comment;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
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

    @Query("select c from Comment c where c.id = :id")
    @Lock(LockModeType.OPTIMISTIC)
    Optional<Comment> findForLikesComment(@Param("id") Long id);
}
