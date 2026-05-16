package com.example.menunfl.controller;

import com.example.menunfl.dto.comment.CommentRequestDto;
import com.example.menunfl.service.CommentService;
import com.example.menunfl.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/comment")
public class CommentController {
    private final CommentService commentService;
    private final UserService userService;

    public CommentController(CommentService commentService, UserService userService) {
        this.commentService = commentService;
        this.userService = userService;
    }

    @PostMapping("/create-comment")
    public ResponseEntity<Void> saveComment(@RequestBody CommentRequestDto dto, JwtAuthenticationToken token) {
        var user = userService.findById(UUID.fromString(token.getName()));
        commentService.createComment(dto, user);
        return ResponseEntity.ok().build();
    }
}
