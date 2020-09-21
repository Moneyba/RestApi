package com.theam.api.converter;

import com.theam.api.dao.RoleDao;
import com.theam.api.dto.UserDto;
import com.theam.api.model.Role;
import com.theam.api.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UserConverter extends GenericConverter<User, UserDto> {

    @Autowired
    private RoleDao roleDao;

    public User convertFromDto(UserDto dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setUsername(dto.getUsername());
        if(dto.getPassword() != null) {
            user.setPassword(dto.getPassword());
        }
        user.setDeleted(false);
        user.setRoles(dto.getRoles().stream()
                .map(role -> roleDao.findByName(role).orElse(null)).collect(Collectors.toList()));
        return user;
    }

    public UserDto convertFromEntity(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setRoles(user.getRoles().stream().map(Role::getName).collect(Collectors.toList()));
        return userDto;
    }
}
