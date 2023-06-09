package com.hanghae.myblog.service;

import com.hanghae.myblog.dto.ResponseDto;
import com.hanghae.myblog.dto.article.ArticleRequestDto;
import com.hanghae.myblog.dto.article.ArticleResponseDto;
import com.hanghae.myblog.entity.Article;
import com.hanghae.myblog.entity.User;
import com.hanghae.myblog.entity.UserRole;
import com.hanghae.myblog.exception.NoArticleException;
import com.hanghae.myblog.exception.NoAuthException;
import com.hanghae.myblog.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.hanghae.myblog.exception.ExceptionMessage.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ArticleService {
    private final ArticleRepository articleRepository;

    @Transactional
    public ResponseDto createArticle(ArticleRequestDto articleRequestDto, User user){
        Article article = ArticleRequestDto.toEntity(articleRequestDto,user);
        articleRepository.save(article);
        return new ResponseDto("게시글 등록 완료",HttpStatus.CREATED.value(), ArticleResponseDto.from(article));
    }

    public ArticleResponseDto findByArticleById(Long articleId){
        Article article = findArticle(articleId);
        return ArticleResponseDto.from(article);
    }
    @Transactional
    public ResponseDto updateArticle(Long articleId, ArticleRequestDto articleRequestDto,User user){
        Article article = findArticle(articleId);
        if(!user.getRole().equals(UserRole.ADMIN)){
            if(!article.getUser().getId().equals(user.getId())){
                throw new NoAuthException(NO_AUTH.getMessage());
            }
        }
        article.updateArticle(articleRequestDto);
        articleRepository.flush();
        return new ResponseDto("게시글 수정 완료",HttpStatus.OK.value(), ArticleResponseDto.from(article));
    }

    public List<ArticleResponseDto> findAllArticle(int page,int size,String sortBy,boolean isAsc){
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page-1, size, sort);
        return articleRepository.findAllByOrderByModifiedAtDesc(pageable).getContent().stream().map(a->ArticleResponseDto.from(a)).collect(Collectors.toList());
    }
    @Transactional
    public ResponseDto deleteArticle(Long articleId,User user){
        Article article = findArticle(articleId);
        if(!user.getRole().equals(UserRole.ADMIN)){
            if(!article.getUser().getId().equals(user.getId())){
                throw new NoAuthException(NO_AUTH.getMessage());
            }
        }
        articleRepository.deleteById(articleId);
        articleRepository.flush();
        return new ResponseDto("삭제 완료",HttpStatus.OK.value());
    }

    private Article findArticle(Long articleId){
        return articleRepository.findById(articleId).orElseThrow(() -> new NoArticleException(NO_ARTICLE.getMessage()));
    }
}
