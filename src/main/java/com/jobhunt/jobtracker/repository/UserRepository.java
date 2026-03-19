package com.jobhunt.jobtracker.repository;

import com.jobhunt.jobtracker.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
