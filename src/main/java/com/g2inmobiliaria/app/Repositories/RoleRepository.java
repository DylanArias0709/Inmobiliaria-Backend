package com.g2inmobiliaria.app.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer>{

    Optional<Role> findByRoleName(String roleName);
}
