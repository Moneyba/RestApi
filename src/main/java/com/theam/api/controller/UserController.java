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

    @GetMapping("{id}")
    public UserDto findUser(@PathVariable long id) {
        return userConverter.convertFromEntity(userService.findById(id));
    }

    @PostMapping
    public UserDto save(@Valid @RequestBody UserDto userDto) {
        return userConverter.convertFromEntity(userService.save(userConverter.convertFromDto(userDto)));
    }

    @PutMapping
    public UserDto update(@RequestBody UserDto userDto) {
        return userConverter.convertFromEntity(userService.update(userConverter.convertFromDto(userDto)));
    }

    @DeleteMapping("/{userId}")
    public void delete(@PathVariable long userId) {
        userService.deleteById(userId);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
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
