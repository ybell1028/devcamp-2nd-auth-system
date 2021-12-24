package com.smilegate.user.repository;

import com.smilegate.user.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends CrudRepository<User, Long> {
    List<User> findAll();
    boolean existsByEmail(String email);
    Optional<User> findByUuid(UUID uuid);
    void deleteByUuid(UUID uuid);
}
