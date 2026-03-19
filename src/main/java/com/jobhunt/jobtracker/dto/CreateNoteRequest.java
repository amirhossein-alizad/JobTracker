package com.jobhunt.jobtracker.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CreateNoteRequest {
    @NotBlank
    private String username;
    @NotBlank
    private String Text;
}
