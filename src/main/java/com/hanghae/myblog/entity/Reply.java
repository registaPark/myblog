package com.hanghae.myblog.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reply extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Comment comment;
    private String content;


    @Builder
    public Reply(String content, Comment comment, User user) {
        this.comment = comment;
        this.content = content;
        this.user = user;
        comment.getReplyList().add(this);
    }

    public Reply updateReply(String content){
        this.content = content;
        return this;
    }
}
