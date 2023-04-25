package com.hanghae.myblog.repository;

import com.hanghae.myblog.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article,Long> {
    @Query("select a from Article a left join fetch a.comments")
    List<Article> findAllByOrderByModifiedAtDesc();
}
