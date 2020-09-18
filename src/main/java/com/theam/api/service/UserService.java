package com.theam.api.service;

import com.theam.api.model.User;

import java.util.List;

public interface UserService {
    List<User> findAll();
    User findById(long id);
    User findByUsername(String username);
    User save(User user);
    User update(User user);
    void deleteById(Long userId);


}
