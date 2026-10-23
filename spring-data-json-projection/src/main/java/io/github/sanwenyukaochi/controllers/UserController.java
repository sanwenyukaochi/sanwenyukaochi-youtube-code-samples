package io.github.sanwenyukaochi.controllers;

import io.github.sanwenyukaochi.dto.UserPayload;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @PostMapping
    public UserPayload createUser(@RequestBody UserPayload userPayload) {
        return userPayload;
    }
}
