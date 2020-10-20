package com.theam.rest.api.dto;

import com.theam.rest.api.constraint.ValidPassword;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

public class UserDto {
    private Long id;

    @NotNull(message = "A username needs to be defined")
    @Email
    private String username;

    @NotEmpty(message = "The user must have a role assigned")
    private Set<String> roles;

    @ValidPassword
    private String password;

    public UserDto() {
    }

    public UserDto(Long id,
                   @NotNull(message = "A username needs to be defined")  @Email String username,
                   @NotEmpty(message = "The user must have a role assigned") Set<String> roles,
                   String password) {
        this.id = id;
        this.username = username;
        this.roles = roles;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
