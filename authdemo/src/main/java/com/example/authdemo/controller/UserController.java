package com.example.authdemo.controller;

import com.example.authdemo.dto.ChangePasswordRequest;
import com.example.authdemo.dto.LoginRequest;
import com.example.authdemo.model.User;
import com.example.authdemo.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    @Autowired
    private UserService userService;

    @PutMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordRequest request) {
        boolean result = userService.changeUserPassword(request.getEmail(), request.getNewPassword());

        if (result) {
            return ResponseEntity.ok("Password updated successfully.");
        } else {
            return ResponseEntity.badRequest().body("User not found or update failed.");
        }
    }

    @PostMapping("/register")
    public String registerUser(@RequestBody User user) {
        User registeredUser = userService.registerUser(user);
        if (registeredUser == null) {
            return "Registration failed. Please try again.";
        }
        return "User registered successfully!";
    }

    @PostMapping("/login")
    public String loginUser(@RequestBody LoginRequest loginRequest) {
        return userService.loginUser(loginRequest);
    }
}