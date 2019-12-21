package com.epam.phone.directory.config;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.epam.phone.directory.service.PhoneDirectoryImporter;
import com.google.common.io.Resources;

@Configuration
public class LoadDefaultPhoneDirectory {

    @Profile(value = "dev")
    @Bean
    public String defaultPhoneDirectory(PhoneDirectoryImporter phoneDirectoryImporter) {
        try {
            URL jsonPath = this.getClass().getResource("/static/phoneDirectory.json");
            String json = Resources.toString(jsonPath, StandardCharsets.UTF_8);
            phoneDirectoryImporter.importPhoneDirectory(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "defaultPhoneDirectory";
    }
}
