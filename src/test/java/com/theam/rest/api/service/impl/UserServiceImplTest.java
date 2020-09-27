package com.theam.rest.api.service.impl;

import com.theam.rest.api.dao.UserDao;
import com.theam.rest.api.exception.NotFoundException;
import com.theam.rest.api.exception.UniqueConstraintException;
import com.theam.rest.api.model.Role;
import com.theam.rest.api.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserDao userDao;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    private final String username = "user@theam.com";

    @Test
    void whenFindAll_shouldCallUserDaoFindAll() {
        userServiceImpl.findAll();
        verify(userDao).findAll();
    }

    @Test
    void whenTheUserIsLoadedById_thenTheUserIsReturned() {
        Long userId = 1L;
        when(userDao.findById(anyLong())).thenReturn(Optional.of(new User()));
        userServiceImpl.findById(userId);
        verify(userDao).findById(userId);
    }

    @Test
    void whenTheUserIsNotFound_thenThereIsANotFoundException() {
        when(userDao.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> userServiceImpl.findById(1L));
    }

    @Test
    void whenANewUserIsCreatedSuccessfully_thenTheUserIsReturned() {
        Role role = new Role(1L, "ROLE_ADMIN");
        User expectedUser = new User();
        expectedUser.setUsername(username);
        expectedUser.setRoles(Collections.singletonList(role));
        when(userDao.findByUsername(username)).thenReturn(Optional.empty());
        when(userDao.save(expectedUser)).then(returnsFirstArg());
        userServiceImpl.save(expectedUser);
        verify(userDao).save(expectedUser);
    }

    @Test
    void whenTheNewUserAlreadyExists_thenThereIsAnUniqueConstraintException() {
        User expectedUser = new User();
        expectedUser.setUsername(username);
        when(userDao.findByUsername(username)).thenReturn(Optional.of(new User()));
        assertThrows(UniqueConstraintException.class, () -> userServiceImpl.save(expectedUser));
    }

    @Test
    void whenAUserIsUpdatedSuccessfully_thenTheUserIsReturned() {
        Long userId = 1L;
        Role role = new Role(1L, "ROLE_ADMIN");
        User expectedUser = new User();
        expectedUser.setId(userId);
        expectedUser.setUsername(username);
        expectedUser.setRoles(Collections.singletonList(role));
        when(userDao.findByUsername(username)).thenReturn(Optional.empty());
        when(userDao.save(expectedUser)).then(returnsFirstArg());
        userServiceImpl.save(expectedUser);
        verify(userDao).save(expectedUser);
    }


}