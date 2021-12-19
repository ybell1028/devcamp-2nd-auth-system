package com.smilegate.auth.repository;

import com.smilegate.auth.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends CrudRepository<User, Long> {
    boolean existsByEmail(String email);
    Optional<User> findByUuid(UUID uuid);
    Optional<User> findByEmail(String email);
}
