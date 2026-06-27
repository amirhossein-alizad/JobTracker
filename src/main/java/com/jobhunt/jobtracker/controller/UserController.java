package com.jobhunt.jobtracker.controller;

import com.jobhunt.jobtracker.Service.UserService;
import com.jobhunt.jobtracker.domain.User;
import com.jobhunt.jobtracker.dto.request.CreateUserRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {
    private UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(@RequestBody CreateUserRequest request) {
        userService.createUser(request);
    }
}
