package com.epam.phone.directory.controller;

import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

import java.util.Collection;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.epam.phone.directory.model.db.PhoneCompany;
import com.epam.phone.directory.model.db.PhoneNumber;
import com.epam.phone.directory.model.db.User;
import com.epam.phone.directory.repository.PhoneCompanyRepository;
import com.epam.phone.directory.repository.PhoneNumberRepository;
import com.epam.phone.directory.repository.UserRepository;
import com.epam.phone.directory.test.utils.TestUtils;

/*
    SpringRunner is an alias for the SpringJUnit4ClassRunner. It is a custom extension of JUnit’s BlockJUnit4ClassRunner
    which provides functionality of the Spring TestContext Framework to standard JUnit tests by means of the TestContextManager
    and associated support classes and annotations.

    https://spring.io/guides/gs/testing-web/
    Another useful approach is to not start the server at all, but test only the layer below that,
    where Spring handles the incoming HTTP request and hands it off to your controller.
    That way, almost the full stack is used, and your code will be called exactly the same way
    as if it was processing a real HTTP request, but without the cost of starting the server.
    To do that we will use Spring’s MockMvc, and we can ask for that to be injected for us
    by using the @AutoConfigureMockMvc annotation on the test case
 */
//@RunWith(SpringRunner.class) // for junit 4
@ExtendWith(SpringExtension.class) // for junit 5
@SpringBootTest
@AutoConfigureMockMvc
public class PhoneDirectoryImportIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PhoneNumberRepository phoneNumberRepository;

    @Autowired
    PhoneCompanyRepository phoneCompanyRepository;

    @Test
    public void bookingManager_shouldBeAble_toImportUsersFromJSONFile_andThenBeRedirectToUsersPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .multipart("/import")
                .file(TestUtils.createMockMultipartFile("/testPhoneDirectory.json", MediaType.APPLICATION_JSON))
                .with(csrf())
                .with(user("manager").roles("BOOKING_MANAGER"))
        )
        .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
        .andExpect(MockMvcResultMatchers.redirectedUrl("/users"));

        Collection<User> users = (Collection<User>) userRepository.findAll();
        MatcherAssert.assertThat(users.size(), is(4));

        Collection<PhoneCompany> phoneCompanies = (Collection<PhoneCompany>) phoneCompanyRepository.findAll();
        MatcherAssert.assertThat(phoneCompanies.size(), is(3));

        Collection<PhoneNumber> phoneNumbers = (Collection<PhoneNumber>) phoneNumberRepository.findAll();
        MatcherAssert.assertThat(phoneNumbers.size(), is(5));
    }

    @Test
    public void bookingManager_shouldBeRedirectToErrorPage_ifImportIsNotSuccessful() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .multipart("/import")
                .file(TestUtils.createMockMultipartFile("/missingFile.json", MediaType.APPLICATION_JSON))
                .with(csrf())
                .with(user("manager").roles("BOOKING_MANAGER"))
        )
        .andExpect(MockMvcResultMatchers.status().is4xxClientError())
        .andReturn();

        result.getResponse().getContentAsString();
    }

    @Test
    public void regularUser_shouldNotHaveAccessToTheImportPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .multipart("/import")
                .file(TestUtils.createMockMultipartFile("/testPhoneDirectory.json", MediaType.APPLICATION_JSON))
                .with(csrf())
                .with(user("user").roles("REGISTERED_USER"))
        )
        .andExpect(MockMvcResultMatchers.status().isForbidden());

        assertThatAllRepositoriesStillDoNotHaveAnyData();
    }

    @Test
    public void anonymousUser_shouldBeRedirectedToLoginPage_ifTryingToAccessImportPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .multipart("/import")
                .file(TestUtils.createMockMultipartFile("/testPhoneDirectory.json", MediaType.APPLICATION_JSON))
                .with(csrf())
        )
        .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
        .andExpect(MockMvcResultMatchers.redirectedUrlPattern("http://*/login"));

        assertThatAllRepositoriesStillDoNotHaveAnyData();
    }

    private void assertThatAllRepositoriesStillDoNotHaveAnyData() {
        Collection<User> users = (Collection<User>) userRepository.findAll();
        MatcherAssert.assertThat(users.size(), is(0));

        Collection<PhoneCompany> phoneCompanies = (Collection<PhoneCompany>) phoneCompanyRepository.findAll();
        MatcherAssert.assertThat(phoneCompanies.size(), is(0));

        Collection<PhoneNumber> phoneNumbers = (Collection<PhoneNumber>) phoneNumberRepository.findAll();
        MatcherAssert.assertThat(phoneNumbers.size(), is(0));
    }
}
