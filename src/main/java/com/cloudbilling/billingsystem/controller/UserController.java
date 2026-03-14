package com.cloudbilling.billingsystem.controller;

import com.cloudbilling.billingsystem.dto.UserDTO;
import com.cloudbilling.billingsystem.service.UserService;
import org.springframework.web.bind.annotation.*;
import com.cloudbilling.billingsystem.model.User;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/all")
public List<User> getAllUsers() {
    return userService.getAllUsers();
}

    @PostMapping
    public UserDTO createUser(@RequestBody com.cloudbilling.billingsystem.model.User user) {
        com.cloudbilling.billingsystem.model.User saved = userService.saveUser(user);
        return new UserDTO(saved.getId(), saved.getName(), saved.getEmail());
    }

    @GetMapping("/{id}")
    public UserDTO getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }
}