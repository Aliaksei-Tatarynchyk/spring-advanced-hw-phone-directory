package com.epam.phone.directory.controller;

import java.io.ByteArrayInputStream;
import java.security.Principal;
import java.util.Collection;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.epam.phone.directory.model.db.User;
import com.epam.phone.directory.service.UserService;
import com.epam.phone.directory.service.pdf.PdfGenerator;

@Controller
@RequestMapping("/users")
public class UserController {

    final UserService userService;

    final PdfGenerator pdfGenerator;

    public UserController(UserService userService, PdfGenerator pdfGenerator) {
        this.userService = userService;
        this.pdfGenerator = pdfGenerator;
    }

    @GetMapping
    public ModelAndView displayAllUsers() {
        return new ModelAndView("users", "users", userService.getAllUsers());
    }

    @GetMapping("/current")
    public ModelAndView displayCurrentUser(Principal principal) {
        String username = principal != null ? principal.getName() : null;
        return new ModelAndView("user", "user", userService.getUserByUsername(username));
    }

    @GetMapping(produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> displayAllUsersAsPDF() {
        Collection<User> users = userService.getAllUsers();
        ByteArrayInputStream bis = pdfGenerator.generateUsersPDF(users);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=users.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }

    @GetMapping(value = "/spring-pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    public ModelAndView displayAllUsersAsPDFViaSpringPDFView() {
        return new ModelAndView("usersPDF", "users", userService.getAllUsers());
    }

}
