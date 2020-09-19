package com.theam.api.controller;

import com.theam.api.converter.UserConverter;
import com.theam.api.dto.UserDto;
import com.theam.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserConverter userConverter;

    @GetMapping
    public List<UserDto> findAll() {
        return userConverter.convertFromEntityCollection(userService.findAll());
    }

    @GetMapping("/{userId}")
    public UserDto findUser(@PathVariable long userId) {
        return userConverter.convertFromEntity(userService.findById(userId));
    }

    @PostMapping("/{userId}")
    public UserDto save(@Valid @RequestBody UserDto userDto) {
        return userConverter.convertFromEntity(userService.save(userDto));
    }

    @PutMapping
    public UserDto update(@PathVariable long userId, @RequestBody UserDto userDto) {
        return userConverter.convertFromEntity(userService.update(userId, userDto));
    }

    @DeleteMapping("/{userId}")
    public void delete(@PathVariable long userId) {
        userService.deleteById(userId);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
