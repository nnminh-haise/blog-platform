package com.proj.projblogplatform.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "blogs")
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "blog_seq_generator")
    @SequenceGenerator(name = "blog_seq_generator", sequenceName = "public.blogs_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "title", nullable = false, unique = true)
    private String title;

    @DateTimeFormat(pattern = "yyyy/MM/dd")
    @Column(name = "create_at", nullable = false)
    private Date createAt;

    @DateTimeFormat(pattern = "yyyy/MM/dd")
    @Column(name = "delete_at")
    private Date deleteAt;

    @DateTimeFormat(pattern = "yyyy/MM/dd")
    @Column(name = "update_at", nullable = false)
    private Date updateAt;

    @DateTimeFormat(pattern = "yyyy/MM/dd")
    @Column(name = "publish_at")
    private Date publishAt;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "attachment")
    private String attachment;

    @Column(name = "slug", nullable = false)
    private String slug;

    @Column(name = "hidden_status", nullable = false)
    private Boolean hiddenStatus = false;

    @OneToMany(mappedBy = "blog", fetch = FetchType.EAGER)
    private Collection<CategoryDetail> categoryDetails;
}
