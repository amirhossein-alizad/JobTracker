package com.jobhunt.jobtracker.controller;

import com.jobhunt.jobtracker.Service.UserService;
import com.jobhunt.jobtracker.dto.request.UserRequest;
import com.jobhunt.jobtracker.dto.response.UserResponse;
import com.jobhunt.jobtracker.exception.UserExistsException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

//@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {
    private UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse createUser(@RequestBody UserRequest request) throws UserExistsException {
        return userService.createUser(request);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public UserResponse loginUser(@RequestBody UserRequest request) {
        return userService.loginUser(request);
    }
}
