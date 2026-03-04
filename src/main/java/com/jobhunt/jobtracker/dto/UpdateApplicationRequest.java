package com.jobhunt.jobtracker.dto;

import com.jobhunt.jobtracker.domain.Status;
import lombok.Getter;


@Getter
public class UpdateApplicationRequest {
    private String roleTitle;
    private String location;
    private Status status;
    private String source;
    private String jobUrl;
    private Integer salaryMin;
    private Integer salaryMax;
}
