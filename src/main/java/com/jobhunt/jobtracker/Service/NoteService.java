package com.jobhunt.jobtracker.Service;

import com.jobhunt.jobtracker.dto.NoteResponse;
import com.jobhunt.jobtracker.exception.NotFoundException;
import com.jobhunt.jobtracker.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {
    @Autowired
    private NoteRepository noteRepository;


    public List<NoteResponse> getAllNotes() {
        return noteRepository.findAll().stream().map(NoteResponse::toResponse).toList();
    }

    public NoteResponse getNoteById(Long id) {
        return noteRepository.findById(id)
                .map(NoteResponse::toResponse)
                .orElseThrow(() -> new NotFoundException("Note not found: " + id));
    }


    public List<NoteResponse> getAllNotesByApplicationId(Long applicationId) {
        return noteRepository.findByApplicationIdOrderByCreatedAtDesc(applicationId).stream()
                .map(NoteResponse::toResponse)
                .toList();
    }
}
