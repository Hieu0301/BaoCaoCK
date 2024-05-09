package com.fit.se.service;

import com.fit.se.entity.User;

import java.util.List;

public interface UserService {

    User saveUser(User user);

    User getUserById(int id);

    List<User> getAllUser();

    void deleteUserById(int id);

    User updateUserById(int id, User newUser);
}
