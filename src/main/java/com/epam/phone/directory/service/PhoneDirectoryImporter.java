package com.epam.phone.directory.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.epam.phone.directory.model.json.PhoneCompany;
import com.epam.phone.directory.model.json.PhoneDirectory;
import com.epam.phone.directory.model.json.PhoneNumber;
import com.epam.phone.directory.model.json.User;
import com.epam.phone.directory.repository.PhoneCompanyRepository;
import com.epam.phone.directory.repository.PhoneNumberRepository;
import com.epam.phone.directory.repository.UserAccountRepository;
import com.epam.phone.directory.repository.UserRepository;
import com.google.gson.Gson;

@Service
public class PhoneDirectoryImporter {

    final UserRepository userRepository;
    final PhoneCompanyRepository phoneCompanyRepository;
    final PhoneNumberRepository phoneNumberRepository;
    final UserAccountRepository userAccountRepository;

    public PhoneDirectoryImporter(UserRepository userRepository, PhoneCompanyRepository phoneCompanyRepository, PhoneNumberRepository phoneNumberRepository,
                                  UserAccountRepository userAccountRepository) {
        this.userRepository = userRepository;
        this.phoneCompanyRepository = phoneCompanyRepository;
        this.phoneNumberRepository = phoneNumberRepository;
        this.userAccountRepository = userAccountRepository;
    }

    public void importPhoneDirectory(MultipartFile importedFile) throws IOException {
        PhoneDirectory phoneDirectory = takePhoneDirectory(importedFile);

        for (User user : phoneDirectory.getUsers()) {
            userRepository.save(user.toJPA(userRepository));
        }

        for (PhoneCompany phoneCompany : phoneDirectory.getPhoneCompanies()) {
            phoneCompanyRepository.save(phoneCompany.toJPA(phoneCompanyRepository));
        }

        for (PhoneNumber phoneNumber : phoneDirectory.getPhoneNumbers()) {
            com.epam.phone.directory.model.db.PhoneNumber jpaPhoneNumber = phoneNumber.toJPA(phoneNumberRepository, userRepository, phoneCompanyRepository);
            if (jpaPhoneNumber.getUser() != null || jpaPhoneNumber.getMobileOperator() != null) {
                phoneNumberRepository.save(jpaPhoneNumber);
            }
        }
    }

    private PhoneDirectory takePhoneDirectory(MultipartFile importedFile) throws IOException {
        String json = new String(importedFile.getBytes(), StandardCharsets.UTF_8);
        PhoneDirectory phoneDirectory = new Gson().fromJson(json, PhoneDirectory.class);
        if (phoneDirectory == null) {
            throw new IllegalArgumentException("Imported file should be a valid json file");
        }
        return phoneDirectory;
    }

}
