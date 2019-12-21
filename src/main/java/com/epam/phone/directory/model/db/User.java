package com.epam.phone.directory.model.db;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;

@Entity
public class User {

    /**
     * All users in the database should have the REGISTERED_USER role by default.
     * User role that is written to DB must have a prefix ROLE_ because it is automatically appended to the roles in
     * {@link com.epam.phone.directory.config.security.SecurityConfiguration}
     */
    public static final String DEFAULT_USER_ROLE = "ROLE_REGISTERED_USER";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    String firstName;
    String lastName;
    String username;
    String password;

    @ElementCollection(fetch = FetchType.EAGER)
    Collection<String> roles = Lists.newArrayList(DEFAULT_USER_ROLE);

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user") // "user" is the name of the field in PhoneNumber entity that corresponds to this relation
    Collection<PhoneNumber> phoneNumbers = new ArrayList<>();

    public User() {
    }

    public User(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    private User(Builder builder) {
        setId(builder.id);
        setFirstName(builder.firstName);
        setLastName(builder.lastName);
        setUsername(builder.username);
        setPassword(builder.password);
        setRoles(builder.roles);
        setPhoneNumbers(builder.phoneNumbers);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private Long id;
        private String firstName;
        private String lastName;
        private String username;
        private String password;
        private Collection<String> roles = Lists.newArrayList(DEFAULT_USER_ROLE);
        private Collection<PhoneNumber> phoneNumbers;

        private Builder() {
        }

        public Builder withId(Long id) {
            this.id = id;
            return this;
        }

        public Builder withFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder withLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder withUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder withPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder withRoles(Collection<String> roles) {
            this.roles = roles;
            return this;
        }

        public Builder withPhoneNumbers(Collection<PhoneNumber> phoneNumbers) {
            this.phoneNumbers = phoneNumbers;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }

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

    public Collection<String> getRoles() {
        return roles;
    }

    public void setRoles(Collection<String> roles) {
        this.roles = roles;
    }

    public Collection<PhoneNumber> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(Collection<PhoneNumber> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public com.epam.phone.directory.model.json.User toJson() {
        com.epam.phone.directory.model.json.User jsonUser = new com.epam.phone.directory.model.json.User();

        if (id != null) {
            jsonUser.setId(id);
        }
        if (firstName != null) {
            jsonUser.setFirstName(firstName);
        }
        if (lastName != null) {
            jsonUser.setLastName(lastName);
        }
        if (username != null) {
            jsonUser.setUsername(username);
        }
        if (!CollectionUtils.isEmpty(phoneNumbers)) {
            jsonUser.setPhoneNumbers(phoneNumbers.stream().map(PhoneNumber::toJson).collect(Collectors.toList()));
        }

        return jsonUser;
    }
}
