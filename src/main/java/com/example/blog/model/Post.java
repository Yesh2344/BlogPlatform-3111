package com.example.blog.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * JPA entity representing a blog post.
 */
@Entity
@Table(name = "posts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Title of the post – must be provided */
    @Column(nullable = false, length = 200)
    private String title;

    /** Raw markdown content */
    @Lob
    @Column(nullable = false)
    private String content;

    /** Timestamp of creation */
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /** Timestamp of last modification */
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    public void onCreate() {
        createdAt = updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}