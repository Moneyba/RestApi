package com.theam.rest.api.dao;

import com.theam.rest.api.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface RoleDao extends JpaRepository<Role, Long> {
    Set<Role> findByNameIn(Set<String> roles);
}
