package com.epam.phone.directory.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.epam.phone.directory.model.json.User;
import com.epam.phone.directory.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserRestController {

    final UserService userService;
    final RequestMappingHandlerMapping handlerMapping;

    public UserRestController(UserService userService, RequestMappingHandlerMapping handlerMapping) {
        this.userService = userService;
        this.handlerMapping = handlerMapping;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers().stream()
                .limit(3)
                .map(com.epam.phone.directory.model.db.User::toJson)
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/{id}")
    public User getUser(@PathVariable Long id) {
        return userService.getUserRepository().findById(id).orElse(new com.epam.phone.directory.model.db.User()).toJson();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public User addUser(@RequestBody User user) {
        com.epam.phone.directory.model.db.User savedUser = userService.getUserRepository().save(user.toJPA());
        return savedUser.toJson();
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public User updateUser(@RequestBody User user, @PathVariable Long id) {
        user.setId(id);
        com.epam.phone.directory.model.db.User savedUser = userService.getUserRepository().save(user.toJPA());
        return savedUser.toJson();
    }

    @PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public User partiallyUpdateUser(@RequestBody User user, @PathVariable Long id) {
        user.setId(id);
        com.epam.phone.directory.model.db.User savedUser = userService.getUserRepository().save(user.toJPA(userService.getUserRepository()));
        return savedUser.toJson();
    }

    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ResponseBody
    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    public String handleHttpMediaTypeNotAcceptableException(Throwable e) {
        return e.getMessage() + ". Acceptable MIME types: " + MediaType.APPLICATION_JSON_VALUE + ", " + MediaType.APPLICATION_PDF_VALUE;
    }
}
