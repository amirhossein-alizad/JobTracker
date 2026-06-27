package com.jobhunt.jobtracker.repository;

import com.jobhunt.jobtracker.domain.User;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    boolean existsByUsername(@NotBlank String username);
}
