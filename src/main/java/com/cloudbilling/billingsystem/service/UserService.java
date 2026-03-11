package com.cloudbilling.billingsystem.service;

import com.cloudbilling.billingsystem.dto.UserDTO;
import com.cloudbilling.billingsystem.model.User;
import com.cloudbilling.billingsystem.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Save User with encrypted password
    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    // Return UserDTO without password
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) return null;
        return new UserDTO(user.getId(), user.getName(), user.getEmail());
    }
}