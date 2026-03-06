package com.jobhunt.jobtracker.dto;

import com.jobhunt.jobtracker.domain.Note;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;

@Getter
@AllArgsConstructor
public class NoteResponse {
    private Long id;
    private Long applicationId;
    private String text;
    private Instant createdAt;

    public static NoteResponse toResponse(Note note) {
        return new NoteResponse(note.getId(), note.getApplication().getId(), note.getText(), note.getCreatedAt());
    }
}
