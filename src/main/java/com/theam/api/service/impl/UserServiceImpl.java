package com.theam.api.service.impl;

import com.theam.api.converter.UserConverter;
import com.theam.api.dao.UserDao;
import com.theam.api.dto.UserDto;
import com.theam.api.exception.NotFoundException;
import com.theam.api.exception.UniqueConstraintException;
import com.theam.api.model.User;
import com.theam.api.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserDao userDao;
    private final UserConverter userConverter;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserDao userDao, UserConverter userConverter, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.userConverter = userConverter;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public User findById(long id) {
        return userDao.findById(id).orElseThrow(()-> new NotFoundException("User not found"));
    }

    @Override
    public User save(UserDto userDto) {
        log.info("Trying to save user with username " + userDto.getUsername());
        userDao.findByUsername(userDto.getUsername())
                .ifPresent(function -> { throw new UniqueConstraintException("A user with the given username already exists");});
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        return userDao.save(userConverter.convertFromDto(userDto));
    }

    @Override
    public User update(Long userId, UserDto userDto) {
        log.info("Trying to update user with username " + userDto.getUsername());
        Optional<User> existingUser = userDao.findByUsername(userDto.getUsername());
        User userFromDb = this.findById(userId);
        if (existingUser.isPresent() && !existingUser.get().getId().equals(userFromDb.getId())) {
            log.error("A user with username " + userDto.getUsername() + " already exists" );
            throw new UniqueConstraintException("A user with the given username already exists");
        }
        User userToUpdate = userConverter.convertFromDto(userDto);
        String password = (userDto.getPassword().isEmpty() || userDto.getPassword() == null) ?
                userFromDb.getPassword() :
                passwordEncoder.encode(userDto.getPassword());
        userToUpdate.setPassword(password);
        return userDao.save(userToUpdate);
    }

    @Override
    public void deleteById(Long userId) {
        User user = findById(userId);
        user.setDeleted(false);
        userDao.save(user);
    }


}
