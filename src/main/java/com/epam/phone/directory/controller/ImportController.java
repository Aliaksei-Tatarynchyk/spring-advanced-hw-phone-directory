package com.epam.phone.directory.controller;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.epam.phone.directory.model.form.PhoneDirectoryImportForm;
import com.epam.phone.directory.model.json.PhoneCompany;
import com.epam.phone.directory.model.json.PhoneDirectory;
import com.epam.phone.directory.model.json.PhoneNumber;
import com.epam.phone.directory.model.json.User;
import com.epam.phone.directory.repository.PhoneCompanyRepository;
import com.epam.phone.directory.repository.PhoneNumberRepository;
import com.epam.phone.directory.repository.UserRepository;
import com.google.gson.Gson;

@Controller
@RequestMapping("/import")
public class ImportController {

    final UserRepository userRepository;
    final PhoneCompanyRepository phoneCompanyRepository;
    final PhoneNumberRepository phoneNumberRepository;

    public ImportController(UserRepository userRepository, PhoneCompanyRepository phoneCompanyRepository, PhoneNumberRepository phoneNumberRepository) {
        this.userRepository = userRepository;
        this.phoneCompanyRepository = phoneCompanyRepository;
        this.phoneNumberRepository = phoneNumberRepository;
    }

    @PostMapping
    public ModelAndView importPhoneDirectory(@ModelAttribute("phoneDirectory") PhoneDirectoryImportForm form) throws IOException {
        String json = new String(form.getFile().getBytes(), StandardCharsets.UTF_8);
        PhoneDirectory phoneDirectory = new Gson().fromJson(json, PhoneDirectory.class);

        for (User user : phoneDirectory.getUsers()) {
            userRepository.save(user.toJPA());
        }

        for (PhoneCompany phoneCompany : phoneDirectory.getPhoneCompanies()) {
            phoneCompanyRepository.save(phoneCompany.toJPA());
        }

        for (PhoneNumber phoneNumber : phoneDirectory.getPhoneNumbers()) {
            com.epam.phone.directory.model.db.PhoneNumber jpaPhoneNumber = phoneNumber.toJPA();
            userRepository.findById(phoneNumber.getUserId()).ifPresent(it -> jpaPhoneNumber.setUser(it));
            phoneCompanyRepository.findById(phoneNumber.getCompanyId()).ifPresent(it -> jpaPhoneNumber.setPhoneCompany(it));
            if (jpaPhoneNumber.getUser() != null || jpaPhoneNumber.getPhoneCompany() != null) {
                phoneNumberRepository.save(jpaPhoneNumber);
            }
        }

        return new ModelAndView("redirect:/users");
    }

}
