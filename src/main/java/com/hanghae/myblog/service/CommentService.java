package com.hanghae.myblog.service;

import com.hanghae.myblog.dto.ResponseDto;
import com.hanghae.myblog.dto.comment.CommentRequestDto;
import com.hanghae.myblog.dto.comment.CommentResponseDto;
import com.hanghae.myblog.dto.reply.ReplyRequestDto;
import com.hanghae.myblog.dto.reply.ReplyResponseDto;
import com.hanghae.myblog.entity.*;
import com.hanghae.myblog.exception.NoArticleException;
import com.hanghae.myblog.exception.NoAuthException;
import com.hanghae.myblog.exception.NoCommentException;
import com.hanghae.myblog.repository.ArticleRepository;
import com.hanghae.myblog.repository.CommentRepository;
import com.hanghae.myblog.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.hanghae.myblog.exception.ExceptionMessage.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {
    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;
    private final ReplyRepository replyRepository;

    @Transactional
    public ResponseDto createComment(Long articleId,CommentRequestDto commentRequestDto, User user){
        Article article = getArticle(articleId);
        Comment comment = CommentRequestDto.toEntity(commentRequestDto, article, user);
        commentRepository.save(comment);
        return new ResponseDto("댓글 작성 완료", HttpStatus.OK.value(),CommentResponseDto.from(comment));
    }

    public CommentResponseDto findComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new NoCommentException("댓글이 없습니다."));
        return CommentResponseDto.from(comment);
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
    private Comment getComment(Long commentId){
        return commentRepository.findById(commentId).orElseThrow(() -> new NoCommentException(NO_COMMENT.getMessage()));
    }

    @Transactional
    public ResponseDto createReply(Long commentId, User user, ReplyRequestDto replyRequestDto) {
        Comment comment = getComment(commentId);
        Reply reply = ReplyRequestDto.toEntity(replyRequestDto, comment, user);
        replyRepository.save(reply);
        return new ResponseDto("대댓글 작성 완료",200, ReplyResponseDto.from(reply));
    }

}
