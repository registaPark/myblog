package com.hanghae.myblog.controller;

import com.hanghae.myblog.dto.ResponseDto;
import com.hanghae.myblog.dto.comment.CommentRequestDto;
import com.hanghae.myblog.dto.comment.CommentResponseDto;
import com.hanghae.myblog.dto.reply.ReplyRequestDto;
import com.hanghae.myblog.security.UserDetailsImpl;
import com.hanghae.myblog.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @Secured("ROLE_ADMIN")
    @GetMapping("/all")
    public   List<CommentResponseDto> allComments(){
        return commentService.findAllComment();
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<CommentResponseDto> findComment(@PathVariable Long commentId){return ResponseEntity.ok(commentService.findComment(commentId));}

    @PostMapping("article/{articleId}")
    public ResponseEntity<ResponseDto> createComment(@PathVariable Long articleId ,CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return ResponseEntity.ok(commentService.createComment(articleId,commentRequestDto,userDetails.getUser()));
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<ResponseDto> updateComment(@PathVariable Long commentId, @RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return ResponseEntity.ok(commentService.updateComment(commentId,commentRequestDto,userDetails.getUser()));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<ResponseDto> deleteComment(@PathVariable Long commentId,@AuthenticationPrincipal UserDetailsImpl userDetails){
        return ResponseEntity.ok(commentService.deleteComment(commentId,userDetails.getUser()));
    }
    //대댓글 작성
    @PostMapping("/reply/{commentId}")
    public ResponseEntity<ResponseDto> createReply(@PathVariable Long commentId, @RequestBody ReplyRequestDto replyRequestDto , @AuthenticationPrincipal UserDetailsImpl userDetails){
        return ResponseEntity.ok(commentService.createReply(commentId, userDetails.getUser(),replyRequestDto));
    }
}
