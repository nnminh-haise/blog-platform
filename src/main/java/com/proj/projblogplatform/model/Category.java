package com.proj.projblogplatform.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @DateTimeFormat(pattern = "yyyy/MM/dd")
    @Column(name = "create_at", nullable = false)
    private LocalDateTime createAt;

    @DateTimeFormat(pattern = "yyyy/MM/dd")
    @Column(name = "delete_at")
    private LocalDateTime deleteAt;

    @DateTimeFormat(pattern = "yyyy/MM/dd")
    @Column(name = "update_at", nullable = false)
    private LocalDateTime updateAt;

    @OneToMany(mappedBy = "category", fetch = FetchType.EAGER)
    private Collection<CategoryDetail> categoryDetails;
}
