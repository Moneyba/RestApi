package com.theam.rest.api.controller;

import com.theam.rest.api.converter.UserConverter;
import com.theam.rest.api.dto.UserDto;
import com.theam.rest.api.model.User;
import com.theam.rest.api.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequestMapping("api/user")
public class UserController {

    private final UserService userService;

    private final UserConverter userConverter;

    public UserController(UserService userService, UserConverter userConverter) {
        this.userService = userService;
        this.userConverter = userConverter;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserDto> findAll() {
        return userConverter.convertFromEntityCollection(userService.findAll());
    }

    @GetMapping(path = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDto findUser(@PathVariable Long userId) {
        return userConverter.convertFromEntity(userService.findById(userId));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDto save(@Valid @RequestBody UserDto userDto) {
        User user = userConverter.convertFromDto(userDto);
        return userConverter.convertFromEntity(userService.save(user));
    }

    @PutMapping(path = "/{userId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDto update(@PathVariable Long userId, @Valid @RequestBody UserDto userDto) {
        User user = userConverter.convertFromDto(userDto);
        return userConverter.convertFromEntity(userService.update(userId, user));
    }

    @DeleteMapping("/{userId}")
    public void delete(@PathVariable Long userId) {
        userService.deleteById(userId);
    }


}
