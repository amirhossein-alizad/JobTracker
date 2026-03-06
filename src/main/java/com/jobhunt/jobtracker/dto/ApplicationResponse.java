package com.jobhunt.jobtracker.dto;

import com.jobhunt.jobtracker.domain.Application;
import com.jobhunt.jobtracker.domain.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class ApplicationResponse {
    private Long id;
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

    public static ApplicationResponse toResponse(Application e) {
        return new ApplicationResponse(
                e.getId(), e.getCompany(), e.getRoleTitle(), e.getLocation(), e.getStatus(),
                e.getSource(), e.getAppliedDate(), e.getJobUrl(), e.getSalaryMin(), e.getSalaryMax(),
                e.getCreatedAt(), e.getUpdatedAt()
        );
    }

}