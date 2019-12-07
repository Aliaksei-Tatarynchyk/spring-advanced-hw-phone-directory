package com.epam.phone.directory.model.db;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    String firstName;
    String lastName;
    String password;
    String roles = "REGISTERED_USER";

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    Collection<PhoneNumber> phoneNumbers;

    public User() {
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    private User(Builder builder) {
        setId(builder.id);
        setFirstName(builder.firstName);
        setLastName(builder.lastName);
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
        private String password;
        private String roles = "REGISTERED_USER";
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

        public Builder withPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder withRoles(String roles) {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public Collection<PhoneNumber> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(Collection<PhoneNumber> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }
}
