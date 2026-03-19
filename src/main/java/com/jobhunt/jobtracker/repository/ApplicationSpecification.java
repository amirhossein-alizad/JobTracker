package com.jobhunt.jobtracker.repository;

import com.jobhunt.jobtracker.domain.Application;
import com.jobhunt.jobtracker.domain.Status;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

@NoArgsConstructor
public final class ApplicationSpecification {

    public static Specification<Application> usernameContains(String username) {
        return (root, query, cb) ->
                cb.like(cb.lower(root.get("username")), "%" + username.toLowerCase().trim() + "%");
    }

    public static Specification<Application> companyContains(String company) {
        return (root, query, cb) ->
                cb.like(cb.lower(root.get("company")), "%" + company.toLowerCase().trim() + "%");
    }

    public static Specification<Application> roleContains(String role) {
        return (root, query, cb) ->
                cb.like(cb.lower(root.get("roleTitle")), "%" + role.toLowerCase().trim() + "%");
    }

    public static Specification<Application> locationContains(String location) {
        return (root, query, cb) ->
                cb.like(cb.lower(cb.coalesce(root.get("location"), "")), "%" + location.toLowerCase().trim() + "%");
    }

    public static Specification<Application> hasStatus(Status status) {
        return (root, query, cb) -> cb.equal(root.get("status"), status);
    }
}
