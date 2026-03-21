package com.jobhunt.jobtracker.controller;

import com.jobhunt.jobtracker.Service.ApplicationService;
import com.jobhunt.jobtracker.Service.UserService;
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
        return applicationService.createApplicationFromRequest(req, user);
    }

    @GetMapping
    public List<ApplicationResponse> list() {
        //TODO: should only return applications for the current user
        return applicationService.getAllApplications();
    }

    @GetMapping("/{id}")
    public ApplicationResponse get(@PathVariable Long id) {
        //TODO: should only return if the usernames match
        return applicationService.getApplicationById(id);
    }

    @GetMapping
    public List<ApplicationResponse> search(
            @RequestParam(required = false) String company,
            @RequestParam(required = false) String role,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) Status status
    ) {
        return applicationService.searchApplications(company, role, location, status);
    }

    @PatchMapping("/{id}")
    public ApplicationResponse update(@RequestBody UpdateApplicationRequest req, @PathVariable Long id) {
        User user = userService.getUserByUsername(req.getUsername());
        return applicationService.updateApplication(id, user, req);
    }

}