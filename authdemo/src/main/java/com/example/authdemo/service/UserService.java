package com.example.authdemo.service;

import com.example.authdemo.dto.LoginRequest;
import com.example.authdemo.model.User;
import com.example.authdemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public User registerUser(User user) {
        try {
            user.setRole("USER");
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return userRepository.save(user);
        } catch (Exception e) {
            System.out.println("Error during registration: " + e.getMessage());
            return null;
        }
    }

    public String loginUser(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail());

        if (user != null && passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            return "Login successful: " + user.getName() + "|" + user.getEmail();
        } else {
            return "Invalid email or password!";
        }
    }
    public boolean changeUserPassword(String email, String newPassword) {
        User user = userRepository.findByEmail(email);
        if (user == null) return false;

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        return true;
    }



}