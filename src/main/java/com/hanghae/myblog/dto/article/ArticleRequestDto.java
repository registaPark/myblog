package com.hanghae.myblog.dto.article;

import com.hanghae.myblog.entity.Article;
import com.hanghae.myblog.entity.User;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ArticleRequestDto {
    @NotNull(message = "제목은 공백일 수 없습니다.")
    private String title;
    @NotNull(message = "내용은 공백일 수 없습니다.")
    private String content;

    public static Article toEntity(ArticleRequestDto articleRequestDto, User user){
        return Article.builder()
                .title(articleRequestDto.getTitle())
                .content(articleRequestDto.getContent())
                .user(user).build();
    }
}
