package com.jobhunt.jobtracker.repository;

import com.jobhunt.jobtracker.domain.Application;
import com.jobhunt.jobtracker.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long>, JpaSpecificationExecutor<Application> {
    public List<Application> getApplicationsByUser(User user);
}
