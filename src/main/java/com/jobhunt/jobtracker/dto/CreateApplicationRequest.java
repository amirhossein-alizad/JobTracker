package com.jobhunt.jobtracker.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CreateApplicationRequest {
    @NotBlank
    private String username;

    @NotBlank
    private String company;

    @NotBlank
    private String roleTitle;

    private String location;

    private String source;

    private LocalDate appliedDate;
    private String jobUrl;
    private Integer salaryMin;
    private Integer salaryMax;
}