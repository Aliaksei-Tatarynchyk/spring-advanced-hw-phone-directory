package com.epam.phone.directory;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.containsString;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/*
    SpringRunner is an alias for the SpringJUnit4ClassRunner. It is a custom extension of JUnit’s BlockJUnit4ClassRunner
    which provides functionality of the Spring TestContext Framework to standard JUnit tests by means of the TestContextManager
    and associated support classes and annotations.


    - this example do not work properly, it does not resolve dependencies of WebController (VisitsRepository).
    - changing @WebMvcTest(WebController.class) to @SpringBootTest causes MockMvc bean not being resolved. It is fixed by @AutoConfigureMockMvc

    https://spring.io/guides/gs/testing-web/
    Another useful approach is to not start the server at all, but test only the layer below that,
    where Spring handles the incoming HTTP request and hands it off to your controller.
    That way, almost the full stack is used, and your code will be called exactly the same way
    as if it was processing a real HTTP request, but without the cost of starting the server.
    To do that we will use Spring’s MockMvc, and we can ask for that to be injected for us
    by using the @AutoConfigureMockMvc annotation on the test case
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class WebControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void shouldReturnAllAddedUsers() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/add/oleg/schukov"))
                .andExpect(MockMvcResultMatchers.status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.get("/users/add/anna/golubeva"))
                .andExpect(MockMvcResultMatchers.status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.get("/users"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(
                        allOf(
                                containsString("oleg schukov"),
                                containsString("anna golubeva")
                            )));
    }

}
