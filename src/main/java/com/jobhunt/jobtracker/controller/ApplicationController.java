package com.jobhunt.jobtracker.controller;

import com.jobhunt.jobtracker.domain.Application;
import com.jobhunt.jobtracker.domain.Status;
import com.jobhunt.jobtracker.dto.ApplicationResponse;
import com.jobhunt.jobtracker.dto.CreateApplicationRequest;
import com.jobhunt.jobtracker.dto.UpdateApplicationRequest;
import com.jobhunt.jobtracker.exception.NotFoundException;
import com.jobhunt.jobtracker.repository.ApplicationRepository;
import com.jobhunt.jobtracker.repository.ApplicationSpecification;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/applications")
public class ApplicationController {

    @Autowired
    private ApplicationRepository repository;

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
        return ApplicationResponse.toResponse(saved);
    }

    @GetMapping
    public List<ApplicationResponse> list() {
        return repository.findAll().stream().map(ApplicationResponse::toResponse).toList();
    }

    @GetMapping("/{id}")
    public ApplicationResponse get(@PathVariable Long id) {
        return repository.findById(id)
                .map(ApplicationResponse::toResponse)
                .orElseThrow(() -> new NotFoundException("Application not found: " + id));
    }

    @GetMapping
    public List<ApplicationResponse> search(
            @RequestParam(required = false) String company,
            @RequestParam(required = false) String role,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) Status status
    ) {
        Specification<Application> spec = Specification.where((Specification<Application>) null);

        if (company != null && !company.isBlank()) {
            spec = spec.and(ApplicationSpecification.companyContains(company));

        }
        if (role != null && !role.isBlank()) {
            spec = spec.and(ApplicationSpecification.roleContains(role));
        }
        if (location != null && !location.isBlank()) {
            spec = spec.and(ApplicationSpecification.locationContains(location));
        }
        if (status != null) {
            spec = spec.and(ApplicationSpecification.hasStatus(status));
        }

        return repository.findAll(spec)
                .stream()
                .map(ApplicationResponse::toResponse)
                .toList();
    }

    @PatchMapping("/{id}")
    public ApplicationResponse update(@RequestBody UpdateApplicationRequest req, @PathVariable Long id) {
        Application application = repository.findById(id).orElseThrow(() -> new NotFoundException("Application not found: " + id));
        application.update(req);
        Application saved = repository.save(application);
        return ApplicationResponse.toResponse(saved);
    }

}