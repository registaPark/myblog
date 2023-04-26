package com.hanghae.myblog.service;

import com.hanghae.myblog.dto.ResponseDto;
import com.hanghae.myblog.entity.Article;
import com.hanghae.myblog.entity.Comment;
import com.hanghae.myblog.entity.Likes;
import com.hanghae.myblog.entity.User;
import com.hanghae.myblog.exception.NoArticleException;
import com.hanghae.myblog.exception.NoCommentException;
import com.hanghae.myblog.repository.ArticleRepository;
import com.hanghae.myblog.repository.CommentRepository;
import com.hanghae.myblog.repository.LikesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class LikesService {
    private final LikesRepository likesRepository;
    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;
    public ResponseDto likeArticle(User user, Long articleId) {
        Article article = articleRepository.findById(articleId).orElseThrow(() -> new NoArticleException("존재하지 않는 게시글"));
        Optional<Likes> likesOptional = likesRepository.findByUserIdAndArticleId(user.getId(),articleId);
        if(likesOptional.isPresent()){
            likesRepository.deleteById(likesOptional.get().getId());
            article.decreaseLikeCount();
            return new ResponseDto("좋아요 취소",200);
        }
        Likes like = Likes.builder().article(article).user(user).build();
        article.increaseLikeCount();
        likesRepository.save(like);
        return new ResponseDto("좋아요 성공",200);
    }

    public ResponseDto likeComment(User user, Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new NoCommentException("존재하지 않는 댓글"));
        Optional<Likes> likesOptional = likesRepository.findByUserIdAndCommentId(user.getId(),commentId);
        if(likesOptional.isPresent()){
            likesRepository.deleteById(likesOptional.get().getId());
            comment.decreaseLikeCount();
            return new ResponseDto("좋아요 취소",200);
        }
        Likes like = Likes.builder().comment(comment).user(user).build();
        comment.increaseLikeCount();
        likesRepository.save(like);
        return new ResponseDto("좋아요 성공",200);
    }
}
