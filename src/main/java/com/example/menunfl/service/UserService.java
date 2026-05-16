package com.example.menunfl.service;

import com.example.menunfl.dto.authentication.RegisterRequest;
import com.example.menunfl.dto.user.UserUpdateRequestDto;
import com.example.menunfl.entity.user.User;
import com.example.menunfl.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public User findById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User with id " + id + " not found"));
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void register(RegisterRequest data) {
        String encryptedPassword = passwordEncoder.encode(data.password());
        var newUser = new User();
        newUser.setEmail(data.email());
        newUser.setPassword(encryptedPassword);
        newUser.setActive(true);
        newUser.setUsername(data.username());
        userRepository.save(newUser);
    }

    public void update(UUID id, UserUpdateRequestDto data){
        var oldUser = findById(id);
        oldUser.setUsername(data.username());
        oldUser.setPhone(data.phone());
        userRepository.save(oldUser);
    }

    public void delete(UUID id) {
        userRepository.deleteById(id);
    }
}
