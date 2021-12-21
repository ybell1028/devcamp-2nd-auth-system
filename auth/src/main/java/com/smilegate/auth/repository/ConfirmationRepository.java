package com.smilegate.auth.repository;

import com.smilegate.auth.entity.Confirmation;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public interface ConfirmationRepository extends CrudRepository<Confirmation,String> {
    Optional<Confirmation> findByIdAndExpirationDateAfterAndExpired(UUID confirmationId, LocalDateTime now, boolean expired);
}
