package com.hanghae.myblog.entity;

import com.hanghae.myblog.dto.article.ArticleRequestDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.boot.context.properties.bind.DefaultValue;

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

    @Column(nullable = false)
    @ColumnDefault("0")
    @Setter
    private int likeCount;

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
