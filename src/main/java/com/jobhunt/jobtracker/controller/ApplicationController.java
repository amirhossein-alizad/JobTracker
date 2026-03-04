package com.jobhunt.jobtracker.controller;

import com.jobhunt.jobtracker.domain.Application;
import com.jobhunt.jobtracker.dto.ApplicationResponse;
import com.jobhunt.jobtracker.dto.CreateApplicationRequest;
import com.jobhunt.jobtracker.repository.ApplicationRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {

    private final ApplicationRepository repository;

    public ApplicationController(ApplicationRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApplicationResponse create(@Valid @RequestBody CreateApplicationRequest req) {
        Application e = new Application();
        e.setCompany(req.getCompany());
        e.setRoleTitle(req.getRoleTitle());
        e.setLocation(req.getLocation());
        e.setSource(req.getSource());
        e.setAppliedDate(req.getAppliedDate());
        e.setJobUrl(req.getJobUrl());
        e.setSalaryMin(req.getSalaryMin());
        e.setSalaryMax(req.getSalaryMax());

        Application saved = repository.save(e);
        return toResponse(saved);
    }

    @GetMapping
    public List<ApplicationResponse> list() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    @GetMapping("/{id}")
    public ApplicationResponse get(@PathVariable UUID id) {
        return repository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new IllegalArgumentException("Application not found: " + id));
    }

    private ApplicationResponse toResponse(Application e) {
        return new ApplicationResponse(
                e.getId(), e.getCompany(), e.getRoleTitle(), e.getLocation(), e.getStatus(),
                e.getSource(), e.getAppliedDate(), e.getJobUrl(), e.getSalaryMin(), e.getSalaryMax(),
                e.getCreatedAt(), e.getUpdatedAt()
        );
    }
}