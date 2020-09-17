package com.theam.api.controller;

import com.theam.api.dao.UserDao;
import com.theam.api.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class UserController {
    @Autowired
    private UserDao userDao;

    @GetMapping("/user/all")
    Iterable<User> all() {
        return userDao.findAll();
    }

    @GetMapping("/user/{id}")
    User userById(@PathVariable Long id) {
        return userDao.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND));
    }

    @PostMapping("/user/save")
    User save(@RequestBody User user) {
        return userDao.save(user);
    }
}
