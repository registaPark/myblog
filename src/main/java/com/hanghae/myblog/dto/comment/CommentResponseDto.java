package com.hanghae.myblog.dto.comment;

import com.hanghae.myblog.entity.Comment;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class CommentResponseDto {
    private Long commentId;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private int likeCount;

    public static CommentResponseDto from(Comment comment){
        return new CommentResponseDto(
                comment.getId(),
                comment.getContent(),
                comment.getCreatedAt(),
                comment.getModifiedAt(),
                comment.getLikeCount());
    }
}
