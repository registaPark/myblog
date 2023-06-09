package com.hanghae.myblog.dto.comment;

import com.hanghae.myblog.entity.Article;
import com.hanghae.myblog.entity.Comment;
import com.hanghae.myblog.entity.User;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CommentRequestDto {
    @NotNull(message = "댓글은 공백일 수 없습니다.")
    private String content;

    public static Comment toEntity(CommentRequestDto commentRequestDto, Article article, User user){
        return Comment.builder().content(commentRequestDto.getContent())
                .article(article)
                .user(user)
                .build();
    }


}
