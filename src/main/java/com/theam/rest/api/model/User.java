package com.theam.rest.api.model;

import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Where(clause = "deleted = false")
@Table(name="\"user\"")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    @NotNull(message = "A username needs to be defined")
    @Email
    private String username;

    @Column(name = "password", nullable = false)
    @NotNull(message = "A password needs to be defined")
    private String password;

    @Column(name = "deleted", nullable = false)
    private Boolean deleted = false;

    @NotEmpty(message = "The user must have a role assigned")
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(
            name = "user_role",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Set<Role> roles;

    public User() {
    }

    public User(Long id, @NotNull(message = "A username needs to be defined") String username, @NotNull(message = "A password needs to be defined") String password, Boolean deleted, @NotEmpty(message = "The user must have a role assigned") Set<Role> roles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.deleted = deleted;
        this.roles = roles;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
