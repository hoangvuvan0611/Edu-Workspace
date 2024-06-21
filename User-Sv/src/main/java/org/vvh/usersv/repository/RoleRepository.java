package org.vvh.usersv.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vvh.usersv.entity.Role;
import org.vvh.usersv.enums.RoleEnum;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(RoleEnum name);
}
