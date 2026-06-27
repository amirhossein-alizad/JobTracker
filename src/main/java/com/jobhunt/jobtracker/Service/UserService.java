package com.jobhunt.jobtracker.Service;

import com.jobhunt.jobtracker.config.SecurityConfig;
import com.jobhunt.jobtracker.domain.User;
import com.jobhunt.jobtracker.dto.request.CreateUserRequest;
import com.jobhunt.jobtracker.exception.NotFoundException;
import com.jobhunt.jobtracker.exception.UserExistsException;
import com.jobhunt.jobtracker.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public User getUserByUsername(String username) {
        return userRepository.findById(username)
                .orElseThrow(() -> new NotFoundException("User not found: " + username));
    }

    public User createUser(CreateUserRequest request) {
        if(userRepository.existsByUsername(request.getUsername())) {
            throw new UserExistsException("Username already exists: " + request.getUsername());
        }
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        return userRepository.save(user);
    }
}
