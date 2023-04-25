package com.hanghae.myblog.dto.article;

import com.hanghae.myblog.entity.Article;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ArticleResponseDto {

    private Long id;
    private String title;
    private String content;

    public static ArticleResponseDto from(Article article){
        return new ArticleResponseDto(article.getId(),article.getTitle(), article.getContent());
    }

}
