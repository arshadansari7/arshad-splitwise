package com.example.splitwire.controller;

import com.example.splitwire.entity.Login;
import com.example.splitwire.entity.User;
import com.example.splitwire.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;

    @PostMapping("/login")
    public String loginWithEmailAndPassword(@RequestBody Login login) {
        Optional<User> userOptional = userRepository.findByEmailAndPassword(login.getEmail(), login.getPassword());

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            log.info("User logged in successfully: {}", user.getUserName());
            return UUID.randomUUID().toString();
        } else {
            log.info("Login failed for email: {}", login.getEmail());
            return "Login failed";
        }
    }

    @PostMapping("/signup")
    public String signup(@RequestBody User user) {
        if (userRepository.findByEmailAndPassword(user.getEmail(), user.getPassword()).isPresent()) {
            log.info("User already exists with email: {}", user.getEmail());
            return "User already exists";
        } else {
            userRepository.save(user);
            log.info("User signed up successfully: {}", user.getUserName());
            return "Signup successful";
        }
    }

}
