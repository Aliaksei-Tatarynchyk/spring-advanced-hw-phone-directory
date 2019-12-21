package com.epam.phone.directory.controller;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.epam.phone.directory.model.form.PhoneDirectoryImportForm;
import com.epam.phone.directory.service.PhoneDirectoryImporter;

@Controller
@RequestMapping("/import")
public class ImportController {

    final PhoneDirectoryImporter phoneDirectoryImporter;

    public ImportController(PhoneDirectoryImporter phoneDirectoryImporter) {
        this.phoneDirectoryImporter = phoneDirectoryImporter;
    }

    @GetMapping
    public String loadImportPage() {
        return "import";
    }

    @PostMapping
    public ModelAndView importPhoneDirectory(@ModelAttribute("phoneDirectory") PhoneDirectoryImportForm form) throws IOException {
        MultipartFile importedFile = form.getFile();
        if (StringUtils.isEmpty(importedFile.getOriginalFilename())) {
            throw new IllegalArgumentException("There is no file to import, please select the file and try again");
        }

        phoneDirectoryImporter.importPhoneDirectory(new String(importedFile.getBytes(), StandardCharsets.UTF_8));

        return new ModelAndView("redirect:/users");
    }

}
