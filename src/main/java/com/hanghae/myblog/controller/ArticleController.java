package com.hanghae.myblog.controller;

import com.hanghae.myblog.dto.ResponseDto;
import com.hanghae.myblog.dto.article.ArticleRequestDto;
import com.hanghae.myblog.dto.article.ArticleResponseDto;
import com.hanghae.myblog.security.UserDetailsImpl;
import com.hanghae.myblog.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/article")
public class ArticleController {
    private final ArticleService articleService;
    @PostMapping("/post")
    public ResponseEntity<ResponseDto> createArticle(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody ArticleRequestDto articleRequestDto){
        return ResponseEntity.ok(articleService.createArticle(articleRequestDto,userDetails.getUser()));
    }

    @GetMapping("/{articleId}")
    public ResponseEntity<ArticleResponseDto> findArticleById(@PathVariable Long articleId){
        return ResponseEntity.ok(articleService.findByArticleById(articleId));
    }

    @PutMapping("/{articleId}")
    public ResponseEntity<ResponseDto> updateArticle(@PathVariable Long articleId, @RequestBody ArticleRequestDto articleRequestDto,@AuthenticationPrincipal UserDetailsImpl userDetails){
        return ResponseEntity.ok(articleService.updateArticle(articleId, articleRequestDto, userDetails.getUser()));
    }

    @DeleteMapping("/{articleId}")
    public ResponseEntity<ResponseDto> deleteArticle(@PathVariable Long articleId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return ResponseEntity.ok(articleService.deleteArticle(articleId, userDetails.getUser()));
    }
    @GetMapping("/all")
    public ResponseEntity<List<ArticleResponseDto>>  findAllArticle(){
        return ResponseEntity.ok(articleService.findAllArticle());
    }
}
