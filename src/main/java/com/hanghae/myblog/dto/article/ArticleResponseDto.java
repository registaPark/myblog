package com.hanghae.myblog.dto.article;

import com.hanghae.myblog.dto.comment.CommentResponseDto;
import com.hanghae.myblog.entity.Article;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class ArticleResponseDto {

    private Long id;
    private String title;
    private String content;
    private List<CommentResponseDto> comments;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public static ArticleResponseDto from(Article article){
        List<CommentResponseDto> comments = article.getComments().stream().map(c -> CommentResponseDto.from(c)).collect(Collectors.toList());
        return new ArticleResponseDto(
                article.getId(),
                article.getTitle(),
                article.getContent(),
                comments,article.getCreatedAt(),
                article.getModifiedAt());
    }

}
