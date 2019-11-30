package com.epam.phone.directory;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import com.epam.phone.directory.model.User;
import com.epam.phone.directory.repository.UserRepository;

@Controller
public class WebController {

    final UserRepository userRepository;

    public WebController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/users")
    public ModelAndView seeAllUsers() {
        return new ModelAndView("users", "users", userRepository.findAll());
    }

    @GetMapping("/users/add/{firstName}/{lastName}")
    public ModelAndView addUser(@PathVariable String firstName, @PathVariable String lastName) {
        User user = User.newBuilder().withFirstName(firstName).withLastName(lastName).build();
        userRepository.save(user);
        return new ModelAndView("addedUser", "user", user);
    }

}
