package com.proj.projblogplatform.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "subscribers")
public class Subscriber {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @DateTimeFormat(pattern = "yyyy/MM/dd")
    @Column(name = "create_at", nullable = false)
    private LocalDateTime createAt;

    @DateTimeFormat(pattern = "yyyy/MM/dd")
    @Column(name = "delete_at")
    private LocalDateTime deleteAt;

    @DateTimeFormat(pattern = "yyyy/MM/dd")
    @Column(name = "update_at", nullable = false)
    private LocalDateTime updateAt;
}
