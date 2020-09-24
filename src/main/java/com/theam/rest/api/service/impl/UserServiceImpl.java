package com.theam.rest.api.service.impl;

import com.theam.rest.api.dao.UserDao;
import com.theam.rest.api.exception.NotFoundException;
import com.theam.rest.api.exception.UniqueConstraintException;
import com.theam.rest.api.model.User;
import com.theam.rest.api.service.UserService;
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
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserDao userDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public User findById(Long id) {
        return userDao.findById(id).orElseThrow(()-> new NotFoundException("User not found"));
    }

    @Override
    public User save(User user) {
        log.info("Trying to save user with username " + user.getUsername());
        userDao.findByUsername(user.getUsername())
                .ifPresent(function -> { throw new UniqueConstraintException("A user with the given username already exists"); });
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userDao.save(user);
    }

    @Override
    public User update(Long userId, User user) {
        log.info("Trying to update user with username " + user.getUsername());
        User userFromDb = this.findById(userId);
        this.checkIfUsernameAlreadyExists(user, userFromDb);
        user.setPassword(this.findPassword(user, userFromDb));
        return userDao.save(user);
    }

    private void checkIfUsernameAlreadyExists(User user, User userFromDb) {
        Optional<User> existingUser = userDao.findByUsername(user.getUsername());
        if (existingUser.isPresent() && !this.updatedUserIdIsEqualToUserFromDbId(existingUser.get(), userFromDb)) {
            log.error("A user with username " + user.getUsername() + " already exists" );
            throw new UniqueConstraintException("A user with the given username already exists");
        }
    }

    private boolean updatedUserIdIsEqualToUserFromDbId(User updatedUser, User userFromDb) {
        return updatedUser.getId().equals(userFromDb.getId());
    }

    private String findPassword(User user, User userFromDb) {
        return (user.getPassword().isEmpty() || user.getPassword() == null) ?
                userFromDb.getPassword() :
                passwordEncoder.encode(user.getPassword());
    }

    @Override
    public void deleteById(Long userId) {
        log.info("Trying to delete user with id " + userId);
        User user = findById(userId);
        user.setDeleted(false);
        userDao.save(user);
    }

}
