package com.vvh.authsv.repository;

import com.vvh.authsv.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserCredentialRepository extends JpaRepository<User, UUID> {
    Optional<User> findByUserName(String userName);
    Optional<User> findById(Long id);
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByUserName(String username);
}
