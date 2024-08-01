package com.rodolfobrandao.coremastermarket.security.repositories;

import com.rodolfobrandao.coremastermarket.security.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
     UserDetails findByLogin(String login);

     Optional<Object> findById(String userId);
}
