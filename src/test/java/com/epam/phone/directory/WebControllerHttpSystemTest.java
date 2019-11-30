package com.epam.phone.directory;

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
public class WebControllerHttpSystemTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void shouldReturnUsersPage() throws Exception {
        Assertions.assertThat(restTemplate.getForObject("http://localhost:" + port + "/users", String.class))
                .contains("Users:");
    }

    @Test
    public void shouldReturnAddedUserPage() throws Exception {
        Assertions.assertThat(restTemplate.getForObject("http://localhost:" + port + "/users/add/oleg/schukov", String.class))
                .contains("User oleg schukov has been added to UserRepository");
    }
}
