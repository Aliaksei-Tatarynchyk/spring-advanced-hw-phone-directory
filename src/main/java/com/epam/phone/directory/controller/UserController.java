package com.epam.phone.directory.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.epam.phone.directory.repository.UserRepository;

@Controller
@RequestMapping("/users")
public class UserController {

    final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public ModelAndView displayAllUsers() {
        return new ModelAndView("users", "users", userRepository.findAll());
    }

}
