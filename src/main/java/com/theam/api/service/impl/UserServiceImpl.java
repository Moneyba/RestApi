package com.theam.api.service.impl;

import com.theam.api.dao.UserDao;
import com.theam.api.exception.UniqueConstraintException;
import com.theam.api.exception.UserNotFoundException;
import com.theam.api.model.User;
import com.theam.api.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public User findById(long id) {
        return userDao.findById(id).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public User findByUsername(String username) {
        return userDao.findByUsername(username).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public User save(User user) {
        log.info("Trying to save user with username " + user.getUsername());
        Optional<User> existingUser = userDao.findByUsername(user.getUsername());
        if (existingUser.isPresent()) {
            log.error("A user with username " + user.getUsername() + " already exists" );
            throw new UniqueConstraintException("A user with the given username already exists");
        }
        return userDao.save(user);
    }

    @Override
    public User update(User user) {
        log.info("Trying to update user with username " + user.getUsername());
        Optional<User> existingUser = userDao.findByUsername(user.getUsername());
        User userFromDb = this.findById(user.getId());
        if (existingUser.isPresent() && !existingUser.get().getId().equals(userFromDb.getId())) {
            log.error("A user with username " + user.getUsername() + " already exists" );
            throw new UniqueConstraintException("A user with the given username already exists");
        }
        return userDao.save(user);
    }

    @Override
    public void deleteById(Long userId) {
        User user = findById(userId);
        user.setEnabled(false);
        userDao.save(user);
    }


}
