package com.jobhunt.jobtracker.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CreateNoteRequest {
    @NotBlank
    private String Text;
}
