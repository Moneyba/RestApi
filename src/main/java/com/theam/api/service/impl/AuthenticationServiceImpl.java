package com.theam.api.service.impl;

import com.theam.api.dao.UserDao;
import com.theam.api.exception.NotFoundException;
import com.theam.api.model.User;
import com.theam.api.service.AuthenticationService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserDao userDao;

    public AuthenticationServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User getLoggedUser() {
        String loggedUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        return this.userDao.findByUsername(loggedUsername).orElseThrow(()-> new NotFoundException("User not found"));
    }
}

