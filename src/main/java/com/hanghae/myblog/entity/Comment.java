package com.hanghae.myblog.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    private Article article;
    private String content;

    @Column(nullable = false)
    @ColumnDefault("0")
    @Setter
    private int likeCount;

    @OneToMany(mappedBy = "parent",orphanRemoval = true,fetch = FetchType.LAZY)
    private List<Comment> child = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parent;

    @Builder
    public Comment(String content, Article article, User user,Comment parent) {
        this.article = article;
        this.content = content;
        this.user = user;
        this.parent = parent;
        if(parent != null) parent.getChild().add(this);
    }

    public Comment updateComment(String content){
        this.content = content;
        return this;
    }
    public void increaseLikeCount(){
        this.likeCount++;
    }
    public void decreaseLikeCount(){
        if(likeCount>0) this.likeCount--;
    }
}
