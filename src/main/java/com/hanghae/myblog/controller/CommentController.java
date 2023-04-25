package com.hanghae.myblog.controller;

import com.hanghae.myblog.dto.ResponseDto;
import com.hanghae.myblog.dto.comment.CommentRequestDto;
import com.hanghae.myblog.dto.comment.CommentResponseDto;
import com.hanghae.myblog.security.UserDetailsImpl;
import com.hanghae.myblog.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @Secured("ROLE_ADMIN")
    @GetMapping("/all")
    public List<CommentResponseDto> allComments(){
        return commentService.findAllComment();
    }
    @PostMapping
    public ResponseEntity<ResponseDto> createComment(@RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return ResponseEntity.ok(commentService.createComment(commentRequestDto,userDetails.getUser()));
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<ResponseDto> updateComment(@PathVariable Long commentId, @RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return ResponseEntity.ok(commentService.updateComment(commentId,commentRequestDto,userDetails.getUser()));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<ResponseDto> deleteComment(@PathVariable Long commentId,@AuthenticationPrincipal UserDetailsImpl userDetails){
        return ResponseEntity.ok(commentService.deleteComment(commentId,userDetails.getUser()));
    }
}
