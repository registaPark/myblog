package com.hanghae.myblog.dto.reply;

import com.hanghae.myblog.dto.comment.CommentRequestDto;
import com.hanghae.myblog.entity.Article;
import com.hanghae.myblog.entity.Comment;
import com.hanghae.myblog.entity.Reply;
import com.hanghae.myblog.entity.User;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ReplyRequestDto {
    @NotNull(message = "댓글은 공백일 수 없습니다.")
    private String content;

    public static Reply toEntity(ReplyRequestDto replyRequestDto, Comment comment, User user){
        return Reply.builder().content(replyRequestDto.getContent())
                .comment(comment)
                .user(user)
                .build();
    }

}
