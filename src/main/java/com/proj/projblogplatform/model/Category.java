package com.proj.projblogplatform.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_seq_generator")
    @SequenceGenerator(name = "category_seq_generator", sequenceName = "public.categories_id_seq", allocationSize = 1)
    private Integer id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") // Use this format for LocalDateTime
    @Column(name = "create_at", nullable = false)
    private Date createAt;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") // Use this format for LocalDateTime
    @Column(name = "delete_at")
    private Date deleteAt;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") // Use this format for LocalDateTime
    @Column(name = "update_at", nullable = false)
    private Date updateAt;

    @OneToMany(mappedBy = "category", fetch = FetchType.EAGER)
    private Collection<CategoryDetail> categoryDetails;
}
