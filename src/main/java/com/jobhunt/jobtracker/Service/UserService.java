package com.jobhunt.jobtracker.Service;

import com.jobhunt.jobtracker.domain.User;
import com.jobhunt.jobtracker.exception.NotFoundException;
import com.jobhunt.jobtracker.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepository userRepository;

    public User getUserByUsername(String username) {
        return userRepository.findById(username)
                .orElseThrow(() -> new NotFoundException("User not found: " + username));
    }
}
