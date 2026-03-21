package com.jobhunt.jobtracker.Service;

import com.jobhunt.jobtracker.domain.Application;
import com.jobhunt.jobtracker.domain.Status;
import com.jobhunt.jobtracker.domain.User;
import com.jobhunt.jobtracker.dto.ApplicationResponse;
import com.jobhunt.jobtracker.dto.CreateApplicationRequest;
import com.jobhunt.jobtracker.dto.UpdateApplicationRequest;
import com.jobhunt.jobtracker.exception.NotFoundException;
import com.jobhunt.jobtracker.exception.UnAuthorizedAccessException;
import com.jobhunt.jobtracker.repository.ApplicationRepository;
import com.jobhunt.jobtracker.repository.ApplicationSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplicationService {
    @Autowired
    private ApplicationRepository applicationRepository;

    public ApplicationResponse createApplicationFromRequest(CreateApplicationRequest req, User user) {
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
        Application saved = applicationRepository.save(e);
        return ApplicationResponse.toResponse(saved);
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

    public List<ApplicationResponse> searchApplications(String company, String role, String location, Status status) {
        Specification<Application> spec = Specification.where((Specification<Application>) null);
        //TODO: add username filter to only return applications for the current user

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

        return applicationRepository.findAll(spec)
                .stream()
                .map(ApplicationResponse::toResponse)
                .toList();
    }
}
