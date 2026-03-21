package com.jobhunt.jobtracker.controller;

import com.jobhunt.jobtracker.Service.ApplicationService;
import com.jobhunt.jobtracker.Service.UserService;
import com.jobhunt.jobtracker.domain.Application;
import com.jobhunt.jobtracker.domain.Status;
import com.jobhunt.jobtracker.domain.User;
import com.jobhunt.jobtracker.dto.ApplicationResponse;
import com.jobhunt.jobtracker.dto.CreateApplicationRequest;
import com.jobhunt.jobtracker.dto.UpdateApplicationRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/applications")
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;
    @Autowired
    private UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApplicationResponse create(@Valid @RequestBody CreateApplicationRequest req) {
        User user = userService.getUserByUsername(req.getUsername());
        Application application = applicationService.createApplicationFromRequest(req, user);
        return ApplicationResponse.toResponse(application);
    }

    @GetMapping
    public List<ApplicationResponse> list() {
        //TODO: should only return applications for the current user
        List<Application> applications = applicationService.getAllApplications();
        return applications.stream().map(ApplicationResponse::toResponse).toList();
    }

    @GetMapping("/{id}")
    public ApplicationResponse get(@PathVariable Long id) {
        //TODO: should only return if the usernames match
        Application application = applicationService.getApplicationById(id);
        return ApplicationResponse.toResponse(application);
    }

    @GetMapping
    public List<ApplicationResponse> search(
            @RequestParam(required = false) String company,
            @RequestParam(required = false) String role,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) Status status
    ) {
        List<Application> applications = applicationService.searchApplications(company, role, location, status);
        return applications.stream().map(ApplicationResponse::toResponse).toList();
    }

    @PatchMapping("/{id}")
    public ApplicationResponse update(@RequestBody UpdateApplicationRequest req, @PathVariable Long id) {
        User user = userService.getUserByUsername(req.getUsername());
        Application application = applicationService.updateApplication(id, user, req);
        return ApplicationResponse.toResponse(application);
    }

}