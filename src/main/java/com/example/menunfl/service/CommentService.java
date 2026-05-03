package com.example.menunfl.service;

import com.example.menunfl.dto.comment.CommentRequestDto;
import com.example.menunfl.entity.comment.Comment;
import com.example.menunfl.entity.user.User;
import com.example.menunfl.repository.CommentRepository;
import com.example.menunfl.repository.OrderRepository;
import com.example.menunfl.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    public CommentService(CommentRepository commentRepository, UserRepository userRepository, OrderRepository orderRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
    }

    public void createComment(CommentRequestDto dto, User userDto) {
        var order = orderRepository.findById(dto.orderId())
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

        var user = userRepository.findById(userDto.getId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Comment comment = new Comment();
        comment.setContent(dto.content());
        comment.setOrder(order);
        comment.setUser(user);

        commentRepository.save(comment);
    }
}
