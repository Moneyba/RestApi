package com.theam.api.service;

import com.theam.api.dto.UserDto;
import com.theam.api.model.User;

import java.util.List;

public interface UserService {
    List<User> findAll();
    User findById(long id);
    User save(UserDto userDto);
    User update(Long userId, UserDto user);
    void deleteById(Long userId);


}
