package com.jobhunt.jobtracker.Service;

import com.jobhunt.jobtracker.domain.Application;
import com.jobhunt.jobtracker.domain.User;
import com.jobhunt.jobtracker.dto.ApplicationResponse;
import com.jobhunt.jobtracker.dto.CreateApplicationRequest;
import com.jobhunt.jobtracker.dto.UpdateApplicationRequest;
import com.jobhunt.jobtracker.exception.NotFoundException;
import com.jobhunt.jobtracker.exception.UnAuthorizedAccessException;
import com.jobhunt.jobtracker.repository.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplicationService {
    @Autowired
    private ApplicationRepository applicationRepository;

    public Application createApplicationFromRequest(CreateApplicationRequest req, User user) {
        Application e = new Application();
        e.setUser(user);
        e.setCompany(req.getCompany());
        e.setRoleTitle(req.getRoleTitle());
        e.setLocation(req.getLocation());
        e.setSource(req.getSource());
        e.setAppliedDate(req.getAppliedDate());
        e.setJobUrl(req.getJobUrl());
        e.setSalaryMin(req.getSalaryMin());
        e.setSalaryMax(req.getSalaryMax());
        return applicationRepository.save(e);
    }

    public List<ApplicationResponse> getAllApplications() {
        return applicationRepository.findAll().stream().map(ApplicationResponse::toResponse).toList();
    }

    public ApplicationResponse getApplicationById(Long id) {
        return applicationRepository.findById(id)
                .map(ApplicationResponse::toResponse)
                .orElseThrow(() -> new NotFoundException("Application not found: " + id));
    }

    public ApplicationResponse updateApplication(Long id, User user, UpdateApplicationRequest req) {
        Application application = applicationRepository.findById(id).orElseThrow(() -> new NotFoundException("Application not found: " + id));
        if (!application.getUser().equals(user))
            throw new UnAuthorizedAccessException("User " + req.getUsername() + " is not authorized to update this application: " + id);
        application.update(req);
        Application saved = applicationRepository.save(application);
        return ApplicationResponse.toResponse(saved);
    }
}
