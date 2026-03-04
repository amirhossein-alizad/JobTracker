package com.jobhunt.jobtracker.repository;

import com.jobhunt.jobtracker.domain.Application;
import com.jobhunt.jobtracker.domain.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    List<Application> findByCompany(String company);

    List<Application> findByRoleTitle(String roleTitle);

    List<Application> findByLocation(String location);

    List<Application> findByStatus(Status status);

    List<Application> findByCompanyAndStatus(String company, Status status);

    List<Application> findByCompanyContainingIgnoreCase(String company);

    List<Application> findByRoleTitleContainingIgnoreCase(String roleTitle);

    List<Application> findByLocationContainingIgnoreCase(String location);
}
