package com.theam.rest.api.converter;

import com.theam.rest.api.dao.RoleDao;
import com.theam.rest.api.dto.UserDto;
import com.theam.rest.api.exception.InvalidFieldException;
import com.theam.rest.api.model.Role;
import com.theam.rest.api.model.User;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserConverter extends GenericConverter<User, UserDto> {

    private static final String HIDDEN_PASSWORD = "*****";

    private final RoleDao roleDao;

    public UserConverter(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    public User convertFromDto(UserDto dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setUsername(dto.getUsername());
        user.setPassword(HIDDEN_PASSWORD.equals(dto.getPassword()) || dto.getPassword() == null ? "" : dto.getPassword());
        user.setDeleted(false);
        Set<Role> roles = roleDao.findByNameIn(dto.getRoles());
        if (roles.size() != dto.getRoles().size()) {throw new InvalidFieldException("Invalid Role");}
        user.setRoles(roles);
        return user;
    }

    public UserDto convertFromEntity(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setPassword(HIDDEN_PASSWORD);
        userDto.setRoles(user.getRoles().stream().map(Role::getName).collect(Collectors.toSet()));
        return userDto;
    }
}
