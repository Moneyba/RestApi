package com.theam.rest.api.model;

import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Where(clause = "deleted = false")
@Table(name="customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    @NotNull(message = "A name needs to be defined")
    private String name;

    @Column(name = "surname", nullable = false, unique = true)
    @NotNull(message = "A surname needs to be defined")
    private String surname;

    @Column(name = "photo_url")
    private String photoUrl;

    @Column(name = "deleted", nullable = false)
    private Boolean deleted = false;

    @ManyToOne
    @NotNull
    private User createdBy;

    @ManyToOne
    private User modifiedBy;

    public Customer() {
    }

    public Customer(Long id, @NotNull(message = "A name needs to be defined") String name, @NotNull(message = "A surname needs to be defined") String surname, String photoUrl, Boolean deleted, @NotNull User createdBy, User modifiedBy) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.photoUrl = photoUrl;
        this.deleted = deleted;
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

    public Boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public User getModifiedBy() { return modifiedBy; }

    public void setModifiedBy(User modifiedBy) { this.modifiedBy = modifiedBy; }
}
