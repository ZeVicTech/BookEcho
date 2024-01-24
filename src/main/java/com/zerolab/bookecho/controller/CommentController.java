package com.zerolab.bookecho.controller;

import com.zerolab.bookecho.config.data.UserSession;
import com.zerolab.bookecho.request.CommentCreate;
import com.zerolab.bookecho.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/review/{reviewId}/comment")
    public Long saveComment(UserSession userSession, @PathVariable Long reviewId, @RequestBody @Valid CommentCreate commentCreate){
        return commentService.save(userSession.getId(), reviewId, commentCreate);
    }

    @DeleteMapping ("/comment/{commentId}")
    public void saveComment(UserSession userSession, @PathVariable Long commentId){
        commentService.delete(userSession.getId(), commentId);
    }
}
