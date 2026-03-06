package com.jobhunt.jobtracker.controller;

import com.jobhunt.jobtracker.dto.NoteResponse;
import com.jobhunt.jobtracker.repository.ApplicationRepository;
import com.jobhunt.jobtracker.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
