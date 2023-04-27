package com.hanghae.myblog.repository;

import com.hanghae.myblog.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article,Long> {
    @Query("select a from Article a left join fetch a.comments order by a.modifiedAt desc")
    List<Article> findAllByOrderByModifiedAtDesc();

    @Query("select a from Article a left join a.comments where a.id = :id")
    Optional<Article> findById(@Param("id") Long id);
}
