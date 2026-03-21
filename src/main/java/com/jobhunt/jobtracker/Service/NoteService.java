package com.jobhunt.jobtracker.Service;

import com.jobhunt.jobtracker.domain.Application;
import com.jobhunt.jobtracker.domain.Note;
import com.jobhunt.jobtracker.domain.User;
import com.jobhunt.jobtracker.dto.CreateNoteRequest;
import com.jobhunt.jobtracker.exception.NotFoundException;
import com.jobhunt.jobtracker.exception.UnAuthorizedAccessException;
import com.jobhunt.jobtracker.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {
    @Autowired
    private NoteRepository noteRepository;


    public List<Note> getAllNotes() {
        return noteRepository.findAll().stream().toList();
    }

    public Note getNoteById(Long id) {
        return noteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Note not found: " + id));
    }


    public List<Note> getAllNotesByApplicationId(Long applicationId) {
        return noteRepository.findByApplicationIdOrderByCreatedAtDesc(applicationId).stream()
                .toList();
    }

    public Note createNoteForApplication(CreateNoteRequest req, Application application, User user) {
        if (!application.getUser().equals(user))
            throw new UnAuthorizedAccessException("User " + req.getUsername() + " is not authorized to update this application: " + application.getId());
        Note note = new Note();
        note.setApplication(application);
        note.setText(req.getText());
        return noteRepository.save(note);
    }
}
