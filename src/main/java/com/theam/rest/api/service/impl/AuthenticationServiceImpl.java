package com.theam.rest.api.service.impl;

import com.theam.rest.api.dao.UserDao;
import com.theam.rest.api.exception.NotFoundException;
import com.theam.rest.api.model.User;
import com.theam.rest.api.service.AuthenticationService;
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

