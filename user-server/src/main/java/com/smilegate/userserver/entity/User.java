package com.smilegate.userserver.entity;

import com.smilegate.userserver.config.BaseTime;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_tb")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable = false)
    private Long id;

    @Column(nullable = false, unique = true)
    private UUID uuid;

    @Column(length = 20, nullable = false)
    private String name;

    @Column(length = 40, nullable = false, unique = true)
    private String email;

    @Column(length = 64, nullable = false)
    private String password;
}
