package com.theam.rest.api.service;

import com.theam.rest.api.model.User;

import java.util.List;

public interface UserService {
    List<User> findAll();
    User findById(Long id);
    User save(User userDto);
    User update(Long userId, User user);
    void deleteById(Long userId);


}
