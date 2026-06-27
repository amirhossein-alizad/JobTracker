package com.jobhunt.jobtracker.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserRequest {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
