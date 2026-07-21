package com.example.splitwire.controller;

import com.example.splitwire.entity.User;
import com.example.splitwire.repo.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/new")
    public User addUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    @GetMapping("/give")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

}
