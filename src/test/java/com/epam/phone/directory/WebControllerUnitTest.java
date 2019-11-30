package com.epam.phone.directory;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.epam.phone.directory.model.User;
import com.epam.phone.directory.repository.UserRepository;

/*
    https://howtodoinjava.com/spring-boot2/testing/spring-boot-mockmvc-example/
    https://www.baeldung.com/spring-boot-testing
    To test the Controllers, we can use @WebMvcTest. It will auto-configure the Spring MVC infrastructure for our unit tests.
    In most of the cases, @WebMvcTest will be limited to bootstrap a single controller.
    It is used along with @MockBean to provide mock implementations for required dependencies.
    @WebMvcTest also auto-configures MockMvc which offers a powerful way of easy testing MVC controllers without starting a full HTTP server.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(WebController.class)
public class WebControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @Test
    public void shouldAddUserToUserRepository() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/add/oleg/schukov"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        Mockito.verify(userRepository).save(userCaptor.capture());

        assertThat(userCaptor.getValue().getFirstName(), is("oleg"));
        assertThat(userCaptor.getValue().getLastName(), is("schukov"));
    }
}
