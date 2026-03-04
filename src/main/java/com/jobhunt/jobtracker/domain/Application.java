package com.jobhunt.jobtracker.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "applications")
@Getter
@Setter
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String company;

    @Column(name = "role_title", nullable = false)
    private String roleTitle;

    @Column
    private String location;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @Column
    private String source;

    @Column(name = "applied_date")
    private LocalDate appliedDate;

    @Column(name = "job_url", columnDefinition = "TEXT")
    private String jobUrl;

    @Column(name = "salary_min")
    private Integer salaryMin;

    @Column(name = "salary_max")
    private Integer salaryMax;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @PrePersist
    void prePersist() {
        Instant now = Instant.now();
        createdAt = now;
        updatedAt = now;
        if (status == null) status = Status.APPLIED;
    }

    @PreUpdate
    void preUpdate() {
        updatedAt = Instant.now();
    }
}