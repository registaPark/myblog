package com.hanghae.myblog.dto.comment;

import com.hanghae.myblog.dto.reply.ReplyResponseDto;
import com.hanghae.myblog.entity.Comment;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class CommentResponseDto {
    private Long commentId;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private int likeCount;
    private List<ReplyResponseDto> replyList;

    public static CommentResponseDto from(Comment comment){
        return new CommentResponseDto(
                comment.getId(),
                comment.getContent(),
                comment.getCreatedAt(),
                comment.getModifiedAt(),
                comment.getLikeCount(),
                comment.getReplyList().stream().map(r -> ReplyResponseDto.from(r)).collect(Collectors.toList())
        );
    }
}
