package com.epam.phone.directory.model.db;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class PhoneCompany {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    String name;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id")
    UserAccount userAccount;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "mobileOperator")
    Collection<PhoneNumber> phoneNumbers;

    public PhoneCompany() {
    }

    public PhoneCompany(Long id) {
        this.id = id;
    }

    private PhoneCompany(Builder builder) {
        setId(builder.id);
        setName(builder.name);
        setUserAccount(builder.userAccount);
        setPhoneNumbers(builder.phoneNumbers);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private Long id;
        private String name;
        private UserAccount userAccount;
        private Collection<PhoneNumber> phoneNumbers;

        private Builder() {
        }

        public Builder withId(Long id) {
            this.id = id;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withUserAccount(UserAccount userAccount) {
            this.userAccount = userAccount;
            return this;
        }

        public Builder withPhoneNumbers(Collection<PhoneNumber> phoneNumbers) {
            this.phoneNumbers = phoneNumbers;
            return this;
        }

        public PhoneCompany build() {
            return new PhoneCompany(this);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserAccount getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    public Collection<PhoneNumber> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(Collection<PhoneNumber> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }
}
