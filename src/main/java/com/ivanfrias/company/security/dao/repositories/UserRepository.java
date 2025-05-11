package com.ivanfrias.company.security.dao.repositories;

import com.ivanfrias.company.security.dao.models.entities.UserEntity;
import com.ivanfrias.company.security.dao.models.enums.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);
    List<UserEntity> findAllByRole(RoleEnum role);
}

