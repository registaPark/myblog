package com.hanghae.myblog.controller;


import com.hanghae.myblog.dto.ResponseDto;
import com.hanghae.myblog.security.UserDetailsImpl;
import com.hanghae.myblog.service.LikesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/like")
@RequiredArgsConstructor
public class LikesController {
    private final LikesService likeService;

    @PostMapping("article/{articleId}")
    public ResponseEntity<ResponseDto> likeArticle(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long articleId){
        return ResponseEntity.ok(likeService.likeArticle(userDetails.getUser(),articleId));
    }

    @PostMapping("/comment/{commentId}")
    public ResponseEntity<ResponseDto> likeComment(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long commentId){
        return ResponseEntity.ok(likeService.likeComment(userDetails.getUser(),commentId));
    }
}
