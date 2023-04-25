package com.hanghae.myblog.service;

import com.hanghae.myblog.dto.ResponseDto;
import com.hanghae.myblog.dto.comment.CommentRequestDto;
import com.hanghae.myblog.dto.comment.CommentResponseDto;
import com.hanghae.myblog.entity.Article;
import com.hanghae.myblog.entity.Comment;
import com.hanghae.myblog.entity.User;
import com.hanghae.myblog.entity.UserRole;
import com.hanghae.myblog.exception.ExceptionMessage;
import com.hanghae.myblog.exception.NoArticleException;
import com.hanghae.myblog.exception.NoAuthException;
import com.hanghae.myblog.repository.ArticleRepository;
import com.hanghae.myblog.repository.CommentRepository;
import com.hanghae.myblog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.hanghae.myblog.exception.ExceptionMessage.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {
    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;

    @Transactional
    public ResponseDto createComment(CommentRequestDto commentRequestDto, User user){
        Article article = getArticle(commentRequestDto.getArticleId());
        Comment comment = Comment.builder().article(article).user(user).content(commentRequestDto.getContent()).build();
        commentRepository.save(comment);
        return new ResponseDto("댓글 작성 완료", HttpStatus.OK.value(),CommentResponseDto.from(comment));
    }
    @Transactional
    public ResponseDto updateComment(Long commentId, CommentRequestDto commentRequestDto, User user){
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new IllegalArgumentException(NO_COMMENT.getMessage()));
        if(!user.getRole().equals(UserRole.ADMIN)){
            checkCommentUser(user,comment);
        }
        comment.updateComment(commentRequestDto.getContent());
        commentRepository.flush();
        return new ResponseDto("댓글 수정 완료",HttpStatus.OK.value(),CommentResponseDto.from(comment));
    }
    @Transactional
    public ResponseDto deleteComment(Long commentId, User user){
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new IllegalArgumentException(NO_COMMENT.getMessage()));
        if(!user.getRole().equals(UserRole.ADMIN)){
            checkCommentUser(user,comment);
        }
        commentRepository.delete(comment);
        commentRepository.flush();
        return new ResponseDto("댓글 삭제 완료", HttpStatus.OK.value());
    }

    public List<CommentResponseDto> findAllComment(){
        return commentRepository.findAll().stream().map(c->CommentResponseDto.from(c)).collect(Collectors.toList());
    }

    private void checkCommentUser(User user,Comment comment) { // 댓글 주인 검증
        if(!user.getId().equals(comment.getUser().getId())){
            throw new NoAuthException(NO_AUTH.getMessage());
        }
    }

    private Article getArticle(Long articleId) { // 게시글 확인
        return articleRepository.findById(articleId).orElseThrow(() -> new NoArticleException(NO_ARTICLE.getMessage()));
    }
}
