package com.jobhunt.jobtracker.controller;

import com.jobhunt.jobtracker.Service.ApplicationService;
import com.jobhunt.jobtracker.Service.UserService;
import com.jobhunt.jobtracker.domain.Application;
import com.jobhunt.jobtracker.domain.Status;
import com.jobhunt.jobtracker.domain.User;
import com.jobhunt.jobtracker.dto.response.ApplicationResponse;
import com.jobhunt.jobtracker.dto.request.CreateApplicationRequest;
import com.jobhunt.jobtracker.dto.request.UpdateApplicationRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/applications")
@AllArgsConstructor
public class ApplicationController {

    private ApplicationService applicationService;
    private UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApplicationResponse create(@Valid @RequestBody CreateApplicationRequest req, Principal principal) {
        String username = principal.getName();
        User user = userService.getUserByUsername(username);
        Application application = applicationService.createApplicationFromRequest(req, user);
        return ApplicationResponse.toResponse(application);
    }

    @GetMapping
    public List<ApplicationResponse> list(Principal principal) {
        String username = principal.getName();
        User user = userService.getUserByUsername(username);
        List<Application> applications = applicationService.getAllApplications(user);
        return applications.stream().map(ApplicationResponse::toResponse).toList();
    }

    @GetMapping("/{id}")
    @ExceptionHandler(RuntimeException.class)
    public ApplicationResponse get(@PathVariable Long id) {
        //TODO: should only return if the usernames match
        Application application = applicationService.getApplicationById(id);
        return ApplicationResponse.toResponse(application);
    }

    @GetMapping("/search")
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
//        User user = userService.getUserByUsername(req.getUsername());
        Application application = applicationService.updateApplication(id, null, req);
        return ApplicationResponse.toResponse(application);
    }

    @DeleteMapping
    public void delete(@RequestParam Long id) {
        applicationService.deleteApplication(id, null);
    }
}