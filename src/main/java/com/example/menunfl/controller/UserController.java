package com.example.menunfl.controller;

import com.example.menunfl.dto.user.UserUpdateRequestDto;
import com.example.menunfl.dto.user.UserResponseDto;
import com.example.menunfl.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/find-all")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<List<UserResponseDto>> findAll() {
        return ResponseEntity.ok(
                userService
                        .findAll()
                        .stream()
                        .map(UserResponseDto::fromEntity)
                        .toList());
    }

    @GetMapping("/find-by-id/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<UserResponseDto> findById(@PathVariable UUID id) {
        var user = userService.findById(id);
        return ResponseEntity.ok(UserResponseDto.fromEntity(user));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UserResponseDto> update(@PathVariable UUID id, @RequestBody UserUpdateRequestDto data) {
        userService.update(id, data);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        userService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
