package com.epam.phone.directory.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.epam.phone.directory.model.exception.GenericFundsException;
import com.epam.phone.directory.model.form.ChangeMobileOperatorForm;
import com.epam.phone.directory.repository.PhoneCompanyRepository;
import com.epam.phone.directory.repository.PhoneNumberRepository;
import com.epam.phone.directory.service.PhoneNumberService;

@Controller
@RequestMapping("/phoneNumber")
public class PhoneNumberController {

    final PhoneNumberService phoneNumberService;
    final PhoneNumberRepository phoneNumberRepository;
    final PhoneCompanyRepository phoneCompanyRepository;

    public PhoneNumberController(PhoneNumberService phoneNumberService, PhoneNumberRepository phoneNumberRepository, PhoneCompanyRepository phoneCompanyRepository) {
        this.phoneNumberService = phoneNumberService;
        this.phoneNumberRepository = phoneNumberRepository;
        this.phoneCompanyRepository = phoneCompanyRepository;
    }

    @GetMapping("/{phoneNumberId}")
    public ModelAndView displayPhoneNumberPage(@PathVariable Long phoneNumberId) {
        ModelAndView modelAndView = new ModelAndView("phoneNumber");
        modelAndView.getModelMap().put("phoneNumber", phoneNumberRepository.findById(phoneNumberId)
                .orElseThrow(() -> new IllegalArgumentException("Phone number with the id " + phoneNumberId + " does not exist")));
        modelAndView.getModelMap().put("mobileOperators", phoneCompanyRepository.findAll());
        return modelAndView;
    }

    @PostMapping("/{phoneNumberId}")
    public ModelAndView changeMobileOperator(@PathVariable Long phoneNumberId, @ModelAttribute("mobileOperator") ChangeMobileOperatorForm form) {
        Map<String, Object> modelMap = new HashMap<>();
        try {
            phoneNumberService.changeMobileOperator(phoneNumberId, form.getNewMobileOperator());
        } catch (IllegalArgumentException | GenericFundsException e) {
            modelMap.put("error", e.getMessage());
        }
        ModelAndView modelAndView = displayPhoneNumberPage(phoneNumberId);
        modelAndView.getModelMap().putAll(modelMap);
        return modelAndView;
    }
}
