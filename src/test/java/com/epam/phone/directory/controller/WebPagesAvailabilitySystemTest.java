package com.epam.phone.directory.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

/*
    https://spring.io/guides/gs/testing-web/
    It’s nice to have a sanity check like that, but we should also write some tests that assert the behaviour of our application.
    To do that we could start the application up and listen for a connection like it would do in production,
    and then send an HTTP request and assert the response.
    Note the use of webEnvironment=RANDOM_PORT to start the server with a random port (useful to avoid conflicts in test environments),
    and the injection of the port with @LocalServerPort. Also note that Spring Boot has provided a TestRestTemplate for you automatically,
    and all you have to do is @Autowired it.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WebPagesAvailabilitySystemTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void shouldServeHomePage() throws Exception {
        Assertions.assertThat(restTemplate.getForObject("http://localhost:" + port, String.class))
                .as("Home page should be available and should contain the form for uploading the JSON file with phone numbers")
                .contains("data-autotests-id=\"home-page\"")
                .contains("action=\"/import\"")
                .contains("enctype=\"multipart/form-data\"");
    }

    @Test
    public void shouldServeUsersPage() throws Exception {
        Assertions.assertThat(restTemplate.getForObject("http://localhost:" + port + "/users", String.class))
                .as("Users page should be available")
                .contains("data-autotests-id=\"users-page\"");
    }
}
