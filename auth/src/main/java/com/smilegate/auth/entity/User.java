package com.smilegate.auth.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_tb")
public class User extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable = false)
    private Long id;

    @Column(nullable = false, unique = true, columnDefinition = "BINARY(16)")
    private UUID uuid;

    @Column(length = 20, nullable = false)
    private String name;

    @Column(length = 40, nullable = false, unique = true)
    private String email;

    @Column(length = 64, nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    public void modifyRole(Role role) {
        this.role = role;
    }
}
