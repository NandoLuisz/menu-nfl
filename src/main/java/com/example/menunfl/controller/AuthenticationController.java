package com.example.menunfl.controller;

import com.example.menunfl.dto.authentication.*;
import com.example.menunfl.entity.role.Role;
import com.example.menunfl.exception.UserAlreadyExistsException;
import com.example.menunfl.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.Instant;
import java.util.stream.Collectors;

@RequestMapping("api/auth")
@RestController
public class AuthenticationController {

    private final UserService userService;
    private final JwtEncoder jwtEncoder;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public AuthenticationController(UserService userService, JwtEncoder jwtEncoder, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.jwtEncoder = jwtEncoder;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostMapping("/sign-in")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest){
        var user = userService.findByUsername(loginRequest.username());

        if(user.isEmpty() || !user.get().isLoginCorrect(loginRequest, bCryptPasswordEncoder)) {
            throw new BadCredentialsException("Incorrect username or password");
        }

        var now = Instant.now();
        var expiresIn = 3600L;

        var scopes = user.get().getRoles()
                .stream()
                .map(Role::getName)
                .collect(Collectors.joining(" "));

        var claims = JwtClaimsSet.builder()
                .issuer("menu-nfl")
                .subject(user.get().getId().toString())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiresIn))
                .claim("scope", scopes)
                .build();

        var jwsHeader = JwsHeader.with(MacAlgorithm.HS256).build();

        var jwtValue = jwtEncoder.encode(
                JwtEncoderParameters.from(jwsHeader, claims)
        ).getTokenValue();

        return ResponseEntity.ok(new LoginResponse(jwtValue, expiresIn));
    }

    @PostMapping("/sign-up")
    public ResponseEntity<Void> register(
            @RequestBody RegisterRequest data
    ) throws IOException {

        if (userService.findByUsername(data.username()).isPresent()) {
            throw new UserAlreadyExistsException(
                    "User already registered."
            );
        }

        if (userService.findByEmail(data.email()).isPresent()) {
            throw new UserAlreadyExistsException(
                    "Email already registered."
            );
        }

        userService.register(data);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
