package com.epam.phone.directory.controller;

import static org.hamcrest.Matchers.is;

import java.util.Collection;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
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
@RunWith(SpringRunner.class)
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
    public void shouldImportAllDataFromJSONFileAndRedirectToUsersPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .multipart("/import")
                .file(TestUtils.createMockMultipartFile("/testPhoneDirectory.json", MediaType.APPLICATION_JSON))
        )
        .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
        .andExpect(MockMvcResultMatchers.redirectedUrl("/users"));

        Collection<User> users = (Collection<User>) userRepository.findAll();
        Assert.assertThat(users.size(), is(4));

        Collection<PhoneCompany> phoneCompanies = (Collection<PhoneCompany>) phoneCompanyRepository.findAll();
        Assert.assertThat(phoneCompanies.size(), is(3));

        Collection<PhoneNumber> phoneNumbers = (Collection<PhoneNumber>) phoneNumberRepository.findAll();
        Assert.assertThat(phoneNumbers.size(), is(5));
    }

}
