package com.theam.api.service;

import com.theam.api.dto.UserDto;
import com.theam.api.model.User;

import java.util.List;

public interface UserService {
    List<User> findAll();
    User findById(long id);
    User findByUsername(String username);
    User save(UserDto user);
    User update(Long userId, UserDto user);
    void deleteById(Long userId);


}
