package com.epam.phone.directory.controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import com.epam.phone.directory.Application;
import com.epam.phone.directory.service.pdf.PhoneDirectoryImporter;
import com.epam.phone.directory.test.utils.TestUtils;

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
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = { PhoneDirectoryImporter.class, Application.class } )
public class WebPagesAvailabilitySystemTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PhoneDirectoryImporter phoneDirectoryImporter;

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
                .as("Users page should be available if nothing is imported")
                .contains("data-autotests-id=\"users-page\"");
    }

    @Test
    public void shouldDisplayUsersOnUsersPage_afterImportingThemFromJsonFile() throws Exception {
        phoneDirectoryImporter.importPhoneDirectory(TestUtils.createMockMultipartFile("/testPhoneDirectory.json", MediaType.APPLICATION_JSON));

        Assertions.assertThat(restTemplate.getForObject("http://localhost:" + port + "/users", String.class))
                .as("Users page should be available and should display 4 users after importing them from '/testPhoneDirectory.json'")
                .contains("data-autotests-id=\"users-page\"")
                .matches(actualUsersPage -> {
                    Matcher userInfoMatcher = Pattern.compile("data-autotests-id=\"user-info\"").matcher(actualUsersPage);
                    Integer countOfUsersDisplayedOnThePage = Stream.iterate(0, i -> i + 1)
                            .filter(i -> !userInfoMatcher.find())
                            .findFirst()
                            .get();
                    return countOfUsersDisplayedOnThePage == 4;
                });
    }
}
