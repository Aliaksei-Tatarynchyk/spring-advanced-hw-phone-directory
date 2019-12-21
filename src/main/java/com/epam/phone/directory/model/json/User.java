package com.epam.phone.directory.model.json;

import static org.springframework.util.CollectionUtils.isEmpty;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.epam.phone.directory.repository.UserRepository;

public class User {
    Long id;
    String firstName;
    String lastName;
    String username;
    String password;
    List<String> roles;
    List<PhoneNumber> phoneNumbers;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public List<PhoneNumber> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(List<PhoneNumber> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public com.epam.phone.directory.model.db.User toJPA() {
        com.epam.phone.directory.model.db.User jpaUser = new com.epam.phone.directory.model.db.User(id);

        jpaUser.setFirstName(emptyIfNull(firstName));
        jpaUser.setLastName(emptyIfNull(lastName));
        jpaUser.setUsername(emptyIfNull(username));
        jpaUser.setPassword(emptyIfNull(password));
        jpaUser.setRoles(emptyIfNull(roles));

        return jpaUser;
    }

    private String emptyIfNull(String string) {
        return string != null ? string : "";
    }

    private Collection<String> emptyIfNull(Collection<String> collection) {
        return collection != null ? collection : Collections.emptyList();
    }

    public com.epam.phone.directory.model.db.User toJPA(UserRepository userRepository) {
        com.epam.phone.directory.model.db.User jpaUser = Optional.ofNullable(id).map(
                id -> userRepository.findById(id)
                        .orElse(new com.epam.phone.directory.model.db.User(id)))
                .orElse(new com.epam.phone.directory.model.db.User());

        if (firstName != null) {
            jpaUser.setFirstName(firstName);
        }
        if (lastName != null) {
            jpaUser.setLastName(lastName);
        }
        if (username != null) {
            jpaUser.setUsername(username);
        }
        if (password != null) {
            jpaUser.setPassword(password);
        }
        if (!isEmpty(roles)) {
            jpaUser.setRoles(roles);
        }

        return jpaUser;
    }
}
