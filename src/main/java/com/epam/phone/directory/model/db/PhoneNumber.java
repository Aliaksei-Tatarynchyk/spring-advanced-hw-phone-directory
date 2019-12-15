package com.epam.phone.directory.model.db;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class PhoneNumber {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(name = "phone_number", unique = true)
    String value;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    User user;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id")
    UserAccount userAccount;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "mobile_operator_id")
    PhoneCompany mobileOperator;

    public PhoneNumber() {
    }

    public PhoneNumber(Long id) {
        this.id = id;
    }

    private PhoneNumber(Builder builder) {
        setId(builder.id);
        setValue(builder.value);
        setUser(builder.user);
        setUserAccount(builder.userAccount);
        setMobileOperator(builder.mobileOperator);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private Long id;
        private String value;
        private User user;
        private UserAccount userAccount;
        private PhoneCompany mobileOperator;

        private Builder() {
        }

        public Builder withId(Long id) {
            this.id = id;
            return this;
        }

        public Builder withValue(String value) {
            this.value = value;
            return this;
        }

        public Builder withUser(User user) {
            this.user = user;
            return this;
        }

        public Builder withUserAccount(UserAccount userAccount) {
            this.userAccount = userAccount;
            return this;
        }

        public Builder withMobileOperator(PhoneCompany mobileOperator) {
            this.mobileOperator = mobileOperator;
            return this;
        }

        public PhoneNumber build() {
            return new PhoneNumber(this);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserAccount getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    public PhoneCompany getMobileOperator() {
        return mobileOperator;
    }

    public void setMobileOperator(PhoneCompany phoneCompany) {
        this.mobileOperator = phoneCompany;
    }
}
