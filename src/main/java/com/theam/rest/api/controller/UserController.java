package com.theam.rest.api.controller;

import com.theam.rest.api.converter.UserConverter;
import com.theam.rest.api.dto.UserDto;
import com.theam.rest.api.model.User;
import com.theam.rest.api.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequestMapping("api/user")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    private final UserConverter userConverter;

    public UserController(UserService userService, UserConverter userConverter) {
        this.userService = userService;
        this.userConverter = userConverter;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserDto> findAll() {
        log.info("METHOD UserController.findAll");
        return userConverter.convertFromEntityCollection(userService.findAll());
    }

    @GetMapping(path = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDto findUser(@PathVariable Long userId) {
        log.info("METHOD UserController.findUser");
        return userConverter.convertFromEntity(userService.findById(userId));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDto save(@Valid @RequestBody UserDto userDto) {
        log.info("METHOD UserController.save");
        User user = userConverter.convertFromDto(userDto);
        return userConverter.convertFromEntity(userService.save(user));
    }

    @PutMapping(path = "/{userId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDto update(@PathVariable Long userId, @RequestBody UserDto userDto) {
        log.info("METHOD UserController.update");
        User user = userConverter.convertFromDto(userDto);
        return userConverter.convertFromEntity(userService.update(userId, user));
    }

    @DeleteMapping("/{userId}")
    public void delete(@PathVariable Long userId) {
        log.info("METHOD UserController.delete");
        userService.deleteById(userId);
    }

}
