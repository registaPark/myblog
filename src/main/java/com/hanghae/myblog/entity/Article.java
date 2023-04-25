package com.hanghae.myblog.entity;

import com.hanghae.myblog.dto.article.ArticleRequestDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Article extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "article",cascade = CascadeType.ALL)
    @OrderBy("createdAt desc")
    private List<Comment> comments = new ArrayList<>();

    @Builder
    public Article(String title, String content, User user) {
        this.title = title;
        this.content = content;
        this.user = user;
    }

    public Article updateArticle(ArticleRequestDto articleRequestDto){
        this.title = articleRequestDto.getTitle();
        this.content = articleRequestDto.getContent();
        return this;
    }

}
