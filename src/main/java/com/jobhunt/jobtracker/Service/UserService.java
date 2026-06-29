package com.jobhunt.jobtracker.Service;

import com.jobhunt.jobtracker.domain.User;
import com.jobhunt.jobtracker.dto.request.UserRequest;
import com.jobhunt.jobtracker.exception.InvalidCredentialsException;
import com.jobhunt.jobtracker.exception.NotFoundException;
import com.jobhunt.jobtracker.exception.UserExistsException;
import com.jobhunt.jobtracker.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public void createUser(UserRequest request) throws UserExistsException {
        System.out.println(request.getUsername());
        if(userRepository.existsByUsername(request.getUsername())) {
            System.out.println("Username already exists: " + request.getUsername());
            throw new UserExistsException("Username already exists: " + request.getUsername());
        }
        User user = new User();
        System.out.println("HI");
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);
    }

    public void loginUser(UserRequest request) {
        User user = userRepository.findByUsername(request.getUsername());
        if(user == null || !passwordEncoder.matches(request.getPassword(), user.getPassword()))
            throw new InvalidCredentialsException("Invalid Credentials.");
    }
}
