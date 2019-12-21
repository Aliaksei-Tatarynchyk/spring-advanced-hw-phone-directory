package com.epam.phone.directory.controller;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import com.epam.phone.directory.Application;
import com.epam.phone.directory.service.PhoneDirectoryImporter;
import com.google.common.io.Resources;

/*
    https://spring.io/guides/gs/testing-web/
    It’s nice to have a sanity check like that, but we should also write some tests that assert the behaviour of our application.
    To do that we could start the application up and listen for a connection like it would do in production,
    and then send an HTTP request and assert the response.
    Note the use of webEnvironment=RANDOM_PORT to start the server with a random port (useful to avoid conflicts in test environments),
    and the injection of the port with @LocalServerPort. Also note that Spring Boot has provided a TestRestTemplate for you automatically,
    and all you have to do is @Autowired it.
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = { PhoneDirectoryImporter.class, Application.class })
@ActiveProfiles(value = "test") // disable security for tests
public class UserRestSystemTest {

    @LocalServerPort
    private int port;

    private RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private PhoneDirectoryImporter phoneDirectoryImporter;

    @Test
    public void shouldServeUsersAPI() throws Exception {
        URL jsonPath = this.getClass().getResource("/testPhoneDirectory.json");
        String json = Resources.toString(jsonPath, StandardCharsets.UTF_8);
        phoneDirectoryImporter.importPhoneDirectory(json);

        Assertions.assertThat(restTemplate.getForObject("http://localhost:" + port + "/api/users", String.class))
                .as("API should return first 3 users in JSON format if the mediaType is not specified")
                .contains("[{\"id\":1,\"firstName\":\"Oleg\",\"lastName\":\"Schukov\",\"username\":\"oleg_schukov\",\"phoneNumbers\":[{\"id\":1," +
                        "\"value\":\"+1-541-754-3011\",\"companyId\":1}]},{\"id\":2,\"firstName\":\"Anna\",\"lastName\":\"Pavlova\",\"username\":\"aniutka\",\"phoneNumbers\":[{\"id\":2," +
                        "\"value\":\"+1-541-754-3012\",\"companyId\":1},{\"id\":3,\"value\":\"+1-541-754-3013\",\"companyId\":2}]},{\"id\":3,\"firstName\":\"Zheka\"," +
                        "\"lastName\":\"Schuplyj\",\"username\":\"bigBoss\",\"phoneNumbers\":[{\"id\":4,\"value\":\"+1-541-754-3014\"}]}]");

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>("body", headers);
        Assertions.assertThat(restTemplate.exchange("http://localhost:" + port + "/api/users", HttpMethod.GET, entity, String.class).getBody())
                .as("API should return first 3 users in JSON format if the mediaType is application/json (http header \"Accept: application/json\")")
                .contains("[{\"id\":1,\"firstName\":\"Oleg\",\"lastName\":\"Schukov\",\"username\":\"oleg_schukov\",\"phoneNumbers\":[{\"id\":1," +
                        "\"value\":\"+1-541-754-3011\",\"companyId\":1}]},{\"id\":2,\"firstName\":\"Anna\",\"lastName\":\"Pavlova\",\"username\":\"aniutka\",\"phoneNumbers\":[{\"id\":2," +
                        "\"value\":\"+1-541-754-3012\",\"companyId\":1},{\"id\":3,\"value\":\"+1-541-754-3013\",\"companyId\":2}]},{\"id\":3,\"firstName\":\"Zheka\"," +
                        "\"lastName\":\"Schuplyj\",\"username\":\"bigBoss\",\"phoneNumbers\":[{\"id\":4,\"value\":\"+1-541-754-3014\"}]}]");

        headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_PDF));
        entity = new HttpEntity<>("body", headers);
        byte[] pdfBody = restTemplate.exchange("http://localhost:" + port + "/api/users", HttpMethod.GET, entity, byte[].class).getBody();
        String pdfBodyAsString = new String(pdfBody, StandardCharsets.UTF_8);
        Assertions.assertThat(pdfBodyAsString)
                .as("API should return first 3 users in PDF format if the mediaType is application/pdf (http header \"Accept: application/pdf\")")
                .contains("%PDF-1.4");
    }

}
