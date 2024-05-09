package com.fit.se.controller;

import com.fit.se.entity.User;
import com.fit.se.repository.UserRedisRepository;
import com.fit.se.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private UserService userService;
    @Autowired
    private UserRedisRepository userRedisRepository;

    @PostMapping
    public ResponseEntity<?> saveUser(@RequestBody User user) {
        User savedUser = userService.saveUser(user);
        userRedisRepository.saveUser(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        List<User> userList = userRedisRepository.findAll();
        return ResponseEntity.ok(userList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable("id") int userId) {
        User user = userRedisRepository.findById(userId);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable("id") int userId) {
        userService.deleteUserById(userId);
        userRedisRepository.deleteById(userId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUserById(@PathVariable("id") int userId, @RequestBody User newUser) {
       User updatedUser = userService.updateUserById(userId, newUser);
       userRedisRepository.update(updatedUser);
       return ResponseEntity.ok(updatedUser);
    }
}
