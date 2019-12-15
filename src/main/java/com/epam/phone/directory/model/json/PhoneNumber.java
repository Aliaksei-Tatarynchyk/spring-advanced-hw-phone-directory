package com.epam.phone.directory.model.json;

import java.util.Optional;

import com.epam.phone.directory.model.db.UserAccount;
import com.epam.phone.directory.repository.PhoneCompanyRepository;
import com.epam.phone.directory.repository.PhoneNumberRepository;
import com.epam.phone.directory.repository.UserRepository;

public class PhoneNumber {
    Long id;
    String value;
    Long userId;
    Long companyId;
    Double accountBalance;

    public Long getId() {
        return id;
    }

    public String getValue() {
        return value;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public Double getAccountBalance() {
        return accountBalance;
    }

    public com.epam.phone.directory.model.db.PhoneNumber toJPA(PhoneNumberRepository phoneNumberRepository, UserRepository userRepository, PhoneCompanyRepository phoneCompanyRepository) {
        com.epam.phone.directory.model.db.PhoneNumber jpaPhoneNumber = Optional.ofNullable(id).map(
                id -> phoneNumberRepository.findById(id)
                        .orElse(new com.epam.phone.directory.model.db.PhoneNumber(id)))
                .orElse(new com.epam.phone.directory.model.db.PhoneNumber());

        if (value != null) {
            jpaPhoneNumber.setValue(value);
        }

        userRepository.findById(userId).ifPresent(user -> jpaPhoneNumber.setUser(user));
        phoneCompanyRepository.findById(companyId).ifPresent(phoneCompany -> jpaPhoneNumber.setMobileOperator(phoneCompany));

        jpaPhoneNumber.setUserAccount(Optional.ofNullable(jpaPhoneNumber.getUserAccount()).orElse(new UserAccount()));
        if (accountBalance != null) {
            jpaPhoneNumber.getUserAccount().setAccountBalance(accountBalance);
        }

        return jpaPhoneNumber;
    }
}
