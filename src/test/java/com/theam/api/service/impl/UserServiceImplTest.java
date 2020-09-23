package com.theam.api.service.impl;

import com.theam.api.converter.UserConverter;
import com.theam.api.dao.UserDao;
import com.theam.api.dto.UserDto;
import com.theam.api.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.theam.api.service.impl.UserAssert.assertThat;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserDao userDao;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserConverter userConverter;

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @Test
    void whenFindAll_shouldCallUserDaoFindAll() {
        userServiceImpl.findAll();
        verify(userDao).findAll();
    }

    @Test
    void savedUserHasUsername() {
        User user = new User();
        user.setUsername("a@b.c");
        when(userDao.save(any(User.class))).then(returnsFirstArg());
        User savedUser = userServiceImpl.save(user);
        assertThat(savedUser).hasUsername();
    }

}