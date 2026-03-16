package com.jobhunt.jobtracker.controller;

import com.jobhunt.jobtracker.domain.Application;
import com.jobhunt.jobtracker.domain.Note;
import com.jobhunt.jobtracker.dto.CreateNoteRequest;
import com.jobhunt.jobtracker.dto.NoteResponse;
import com.jobhunt.jobtracker.exception.NotFoundException;
import com.jobhunt.jobtracker.repository.ApplicationRepository;
import com.jobhunt.jobtracker.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notes")
public class NoteController {
    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    @GetMapping
    public List<NoteResponse> list() {
        return noteRepository.findAll().stream().map(NoteResponse::toResponse).toList();
    }

    @GetMapping("/{id}")
    public NoteResponse get(@PathVariable Long id) {
        return noteRepository.findById(id)
                .map(NoteResponse::toResponse)
                .orElseThrow(() -> new NotFoundException("Note not found: " + id));
    }

    @GetMapping("/application/{applicationId}")
    public List<NoteResponse> listByApplication(@PathVariable Long applicationId) {
        applicationRepository.findById(applicationId)
                .orElseThrow(() -> new NotFoundException("Application not found: " + applicationId));
        return noteRepository.findByApplicationIdOrderByCreatedAtDesc(applicationId).stream()
                .map(NoteResponse::toResponse)
                .toList();
    }

    @PostMapping("/{applicationId}")
    @ResponseStatus(HttpStatus.CREATED)
    public NoteResponse create(@PathVariable Long applicationId, @RequestBody CreateNoteRequest req) {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new NotFoundException("Application not found: " + applicationId));
        Note note = new Note();
        note.setApplication(application);
        note.setText(req.getText());
        Note saved = noteRepository.save(note);
        return NoteResponse.toResponse(saved);
    }
}
