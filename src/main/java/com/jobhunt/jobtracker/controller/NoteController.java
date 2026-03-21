package com.jobhunt.jobtracker.controller;

import com.jobhunt.jobtracker.Service.ApplicationService;
import com.jobhunt.jobtracker.Service.NoteService;
import com.jobhunt.jobtracker.Service.UserService;
import com.jobhunt.jobtracker.domain.Application;
import com.jobhunt.jobtracker.domain.Note;
import com.jobhunt.jobtracker.domain.User;
import com.jobhunt.jobtracker.dto.CreateNoteRequest;
import com.jobhunt.jobtracker.dto.NoteResponse;
import com.jobhunt.jobtracker.exception.NotFoundException;
import com.jobhunt.jobtracker.exception.UnAuthorizedAccessException;
import com.jobhunt.jobtracker.repository.ApplicationRepository;
import com.jobhunt.jobtracker.repository.NoteRepository;
import com.jobhunt.jobtracker.repository.UserRepository;
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
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NoteService noteService;
    @Autowired
    private ApplicationService applicationService;
    @Autowired
    private UserService userService;

    @GetMapping
    public List<NoteResponse> list() {
        //TODO: should only return notes for applications of the current user
        return noteService.getAllNotes();
    }

    @GetMapping("/{id}")
    public NoteResponse get(@PathVariable Long id) {
        //TODO: should only return if the usernames match
        return noteService.getNoteById(id);
    }

    @GetMapping("/application/{applicationId}")
    public List<NoteResponse> listByApplication(@PathVariable Long applicationId) {
        //TODO: should only return if the usernames match
        //TODO: Should think about checking for existence another way
        applicationService.getApplicationById(applicationId);
        return noteService.getAllNotesByApplicationId(applicationId);
    }

    @PostMapping("/{applicationId}")
    @ResponseStatus(HttpStatus.CREATED)
    public NoteResponse create(@PathVariable Long applicationId, @RequestBody CreateNoteRequest req) {
        User user = userService.getUserByUsername(req.getUsername());
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new NotFoundException("Application not found: " + applicationId));
        if (!application.getUser().equals(user))
            throw new UnAuthorizedAccessException("User " + req.getUsername() + " is not authorized to update this application: " + applicationId);
        Note note = new Note();
        note.setApplication(application);
        note.setText(req.getText());
        Note saved = noteRepository.save(note);
        return NoteResponse.toResponse(saved);
    }
}
