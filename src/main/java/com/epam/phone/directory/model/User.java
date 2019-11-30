package com.epam.phone.directory.model;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class User {

    @Id
    @GeneratedValue
    public Long id;

    String firstName;
    String lastName;

    @Transient
    Collection<PhoneNumber> phoneNumbers;

    public User() {
    }

    private User(Builder builder) {
        firstName = builder.firstName;
        lastName = builder.lastName;
        phoneNumbers = builder.phoneNumbers;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private String firstName;
        private String lastName;
        private Collection<PhoneNumber> phoneNumbers;

        private Builder() {
        }

        public Builder withFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder withLastName(String lastName) {
            this.lastName = lastName;
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

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Collection<PhoneNumber> getPhoneNumbers() {
        return phoneNumbers;
    }

}
