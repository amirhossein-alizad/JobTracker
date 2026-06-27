package com.jobhunt.jobtracker.controller;

import com.jobhunt.jobtracker.Service.ApplicationService;
import com.jobhunt.jobtracker.Service.NoteService;
import com.jobhunt.jobtracker.Service.UserService;
import com.jobhunt.jobtracker.domain.Application;
import com.jobhunt.jobtracker.domain.Note;
import com.jobhunt.jobtracker.dto.request.CreateNoteRequest;
import com.jobhunt.jobtracker.dto.response.NoteResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/notes")
@AllArgsConstructor
public class NoteController {
    private NoteService noteService;
    private ApplicationService applicationService;
    private UserService userService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<NoteResponse> list() {
        //TODO: should only return notes for applications of the current user
        List<Note> notes = noteService.getAllNotes();
        return notes.stream().map(NoteResponse::toResponse).toList();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public NoteResponse get(@PathVariable Long id) {
        //TODO: should only return if the usernames match
        Note note = noteService.getNoteById(id);
        return NoteResponse.toResponse(note);
    }

    @GetMapping("/application/{applicationId}")
    @ResponseStatus(HttpStatus.OK)
    public List<NoteResponse> listByApplication(@PathVariable Long applicationId) {
        //TODO: should only return if the usernames match
        //TODO: Should think about checking for existence another way
        applicationService.getApplicationById(applicationId);
        List<Note> notes = noteService.getAllNotesByApplicationId(applicationId);
        return notes.stream().map(NoteResponse::toResponse).toList();
    }

    @PostMapping("/{applicationId}")
    @ResponseStatus(HttpStatus.CREATED)
    public NoteResponse create(@PathVariable Long applicationId, @RequestBody CreateNoteRequest req) {
//        User user = userService.getUserByUsername(req.getUsername());
        Application application = applicationService.getApplicationById(applicationId);
        Note note = noteService.createNoteForApplication(req, application, null);
        return NoteResponse.toResponse(note);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@RequestParam Long id) {
        noteService.deleteNote(id);
    }
}
