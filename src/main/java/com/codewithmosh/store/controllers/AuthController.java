package com.codewithmosh.store.controllers;

import com.codewithmosh.store.dtos.LoginDto;
import com.codewithmosh.store.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
        var user = userRepository.findUserByEmail(loginDto.getEmail());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("user", "not found"));
        }

        var storedPassword = passwordEncoder.encode(user.getPassword());
        var reportPassword = passwordEncoder.encode(loginDto.getPassword());


        if(!storedPassword.equals(reportPassword)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("password", "unauthorized"));
        }

        return ResponseEntity.ok().build();
    }
}
