package com.smilegate.auth.repository;

import com.smilegate.auth.entity.Confirm;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public interface ConfirmRepository extends CrudRepository<Confirm,String> {
    Optional<Confirm> findByIdAndExpirationDateAfterAndExpired(UUID confirmId, LocalDateTime now, boolean expired);
}
