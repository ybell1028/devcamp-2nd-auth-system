package com.smilegate.userserver.entity;

import com.smilegate.userserver.config.BaseTime;
import lombok.*;

import javax.persistence.*;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_tb")
public class User {
    @Id // UUID로 변경
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable = false)
    private Long id;

    @Column(length = 20, nullable = false)
    private String name;

    @Column(length = 40, nullable = false, unique = true)
    private String email;

    @Column(length = 20, nullable = false, unique = true)
    private String password;
}
