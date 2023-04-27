package com.hanghae.myblog.dto.reply;

import com.hanghae.myblog.entity.Reply;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ReplyResponseDto {
    private Long id;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public static ReplyResponseDto from(Reply reply){
        return new ReplyResponseDto(reply.getId(),reply.getContent(),reply.getCreatedAt(),reply.getModifiedAt());
    }
}
