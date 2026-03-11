package com.cloudbilling.billingsystem.controller;

import com.cloudbilling.billingsystem.exception.InvalidCredentialsException;
import com.cloudbilling.billingsystem.service.UserService;
import com.cloudbilling.billingsystem.dto.UserDTO;
import com.cloudbilling.billingsystem.dto.AuthRequest;
import com.cloudbilling.billingsystem.dto.AuthResponse;
import com.cloudbilling.billingsystem.model.User;
import com.cloudbilling.billingsystem.repository.UserRepository;
import com.cloudbilling.billingsystem.security.JwtUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AuthController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @PostMapping("/register")
    public UserDTO register(@RequestBody com.cloudbilling.billingsystem.model.User user) {
        // userService.saveUser() will encode the password
        com.cloudbilling.billingsystem.model.User saved = userService.saveUser(user);
        return new UserDTO(saved.getId(), saved.getName(), saved.getEmail());
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {
        User user = userRepository.findByEmail(request.getEmail());
        if (user == null || !passwordEncoder.matches(request.getPassword(), user.getPassword())) {
    throw new InvalidCredentialsException();
}
        String token = JwtUtil.generateToken(user.getEmail());
        return new AuthResponse(token);
    }
}