package com.vvh.coresv.repository;

import com.vvh.coresv.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByCode(String code);
    boolean existsByUserName(String userName);
    boolean existsByEmail(String email);
}
