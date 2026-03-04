package com.jobhunt.jobtracker.dto;

import com.jobhunt.jobtracker.domain.Status;
import lombok.Getter;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Getter
public class ApplicationResponse {
    private UUID id;
    private String company;
    private String roleTitle;
    private String location;
    private Status status;
    private String source;
    private LocalDate appliedDate;
    private String jobUrl;
    private Integer salaryMin;
    private Integer salaryMax;
    private Instant createdAt;
    private Instant updatedAt;

    public ApplicationResponse(UUID id, String company, String roleTitle, String location, Status status,
                               String source, LocalDate appliedDate, String jobUrl, Integer salaryMin, Integer salaryMax,
                               Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.company = company;
        this.roleTitle = roleTitle;
        this.location = location;
        this.status = status;
        this.source = source;
        this.appliedDate = appliedDate;
        this.jobUrl = jobUrl;
        this.salaryMin = salaryMin;
        this.salaryMax = salaryMax;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

}