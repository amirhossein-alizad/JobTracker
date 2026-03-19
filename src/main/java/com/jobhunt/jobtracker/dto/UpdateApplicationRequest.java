package com.jobhunt.jobtracker.dto;

import com.jobhunt.jobtracker.domain.Status;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;


@Getter
public class UpdateApplicationRequest {
    @NotBlank
    private String username;
    private String roleTitle;
    private String location;
    private Status status;
    private String source;
    private String jobUrl;
    private Integer salaryMin;
    private Integer salaryMax;
}
