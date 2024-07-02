package com.startup.oda.repository;

import com.startup.oda.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmailAndIsActive(String email, boolean b);
    List<User> findByIsActiveFalseAndDeletionDateBefore(LocalDate date);
}
