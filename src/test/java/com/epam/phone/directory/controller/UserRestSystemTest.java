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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import com.epam.phone.directory.Application;
import com.epam.phone.directory.model.json.User;
import com.epam.phone.directory.service.PhoneDirectoryImporter;
import com.google.common.io.Resources;
import com.google.gson.Gson;

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
    public void shouldServeUsersAPIandDoContentNegotiationForPDFandJSON() throws Exception {
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

    @Test
    public void shouldAddUsersUsingPOSTToUsersAPI_andUpdateUser_UsingPATCHorPUT() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);

        User user = new User();
        user.setFirstName("testFirstName");
        user.setLastName("testLastName");
        user.setUsername("testUsername");
        String body = new Gson().toJson(user, User.class);
        HttpEntity<String> entity = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.exchange("http://localhost:" + port + "/api/users", HttpMethod.POST, entity, String.class);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(response.getBody()).isEqualTo("{\"id\":1,\"firstName\":\"testFirstName\",\"lastName\":\"testLastName\",\"username\":\"testUsername\"}");
        Assertions.assertThat(response.getHeaders().get("Content-Type")).contains("application/json;charset=UTF-8");

        User updatedUser = new User();
        updatedUser.setLastName("updatedLastName");
        body = new Gson().toJson(updatedUser, User.class);
        HttpHeaders patchHeaders = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        MediaType mediaType = new MediaType("application", "merge-patch+json");
        patchHeaders.setContentType(mediaType);
        HttpEntity<String> patchEntity = new HttpEntity<>(body, headers);
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        RestTemplate patchRestTemplate = new RestTemplate(requestFactory);
        response = patchRestTemplate.exchange("http://localhost:" + port + "/api/users/1", HttpMethod.PATCH, patchEntity, String.class);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isEqualTo("{\"id\":1,\"firstName\":\"testFirstName\",\"lastName\":\"updatedLastName\",\"username\":\"testUsername\"}");
        Assertions.assertThat(response.getHeaders().get("Content-Type")).contains("application/json;charset=UTF-8");

        User replacedUser = new User();
        replacedUser.setLastName("fullyReplacedLastName");
        body = new Gson().toJson(replacedUser, User.class);
        entity = new HttpEntity<>(body, headers);
        response = restTemplate.exchange("http://localhost:" + port + "/api/users/1", HttpMethod.PUT, entity, String.class);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isEqualTo("{\"id\":1,\"firstName\":\"\",\"lastName\":\"fullyReplacedLastName\",\"username\":\"\"}");
        Assertions.assertThat(response.getHeaders().get("Content-Type")).contains("application/json;charset=UTF-8");
    }

}
