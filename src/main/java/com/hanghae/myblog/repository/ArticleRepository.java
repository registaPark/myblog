package com.hanghae.myblog.repository;

import com.hanghae.myblog.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article,Long> {
}
