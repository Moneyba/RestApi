package com.theam.rest.api.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class CustomerDto {

    private Long id;

    @NotNull(message = "A name needs to be defined")
    @Size(min= 2, max = 20)
    @Pattern(regexp = "^([A-zÀ-ú]+\\s)*[A-zÀ-ú]+$", message = "Name must contains only letters or spaces")
    private String name;

    @NotNull(message = "A surname needs to be defined.")
    @Size(min= 2, max = 15)
    @Pattern(regexp = "^[A-zÀ-ú]+$", message = "Surname must be a string")
    private String surname;

    private String photoUrl;

    private UserDto createdBy;

    private UserDto modifiedBy;

    public CustomerDto() {
    }

    public CustomerDto(Long id,
                       @NotNull(message = "A name needs to be defined")
                       @Size(min = 2, max = 20)
                       @Pattern(regexp = "^([A-zÀ-ú]+\\s)*[A-zÀ-ú]+$", message = "Name must contains only letters or spaces")
                               String name,
                       @NotNull(message = "A surname needs to be defined.")
                       @Size(min = 2, max = 15)
                       @Pattern(regexp = "^[A-zÀ-ú]+$", message = "Surname must be a string")
                               String surname,
                       String photoUrl,
                       UserDto createdBy,
                       UserDto modifiedBy) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.photoUrl = photoUrl;
        this.createdBy = createdBy;
        this.modifiedBy = modifiedBy;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public UserDto getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UserDto createdBy) {
        this.createdBy = createdBy;
    }

    public UserDto getModifiedBy() { return modifiedBy; }

    public void setModifiedBy(UserDto modifiedBy) { this.modifiedBy = modifiedBy; }
}
