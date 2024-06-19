package com.g2inmobiliaria.app.Repositories;

import com.g2inmobiliaria.app.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUserName(String username);
}
