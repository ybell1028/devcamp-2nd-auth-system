package com.smilegate.auth.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Confirm extends BaseTime {
    private static final long CONFIRM_EXPIRATION_MINUTE = 5L;

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(nullable = false, unique = true, columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(nullable = false, unique = true, columnDefinition = "BINARY(16)")
    private UUID userUuid;

    @Column(nullable = false)
    private boolean expired;

    @Column(nullable = false)
    private LocalDateTime expirationDate;

    public static Confirm makeConfirm(UUID userUuid){
        return Confirm.builder()
                .userUuid(userUuid)
                .expired(false)
                .expirationDate(LocalDateTime.now().plusMinutes(CONFIRM_EXPIRATION_MINUTE))
                .build();
    }

    public void useToken(){
        expired = true;
    }
}
