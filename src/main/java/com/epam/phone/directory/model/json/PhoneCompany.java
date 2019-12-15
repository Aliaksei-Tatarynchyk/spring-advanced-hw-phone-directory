package com.epam.phone.directory.model.json;

import java.util.Optional;

import com.epam.phone.directory.model.db.UserAccount;
import com.epam.phone.directory.repository.PhoneCompanyRepository;

public class PhoneCompany {
    Long id;
    String name;
    Double accountBalance;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getAccountBalance() {
        return accountBalance;
    }

    public com.epam.phone.directory.model.db.PhoneCompany toJPA(PhoneCompanyRepository phoneCompanyRepository) {
        com.epam.phone.directory.model.db.PhoneCompany jpaPhoneCompany = Optional.ofNullable(id).map(
                id -> phoneCompanyRepository.findById(id)
                        .orElse(new com.epam.phone.directory.model.db.PhoneCompany(id)))
                .orElse(new com.epam.phone.directory.model.db.PhoneCompany());

        if (name != null) {
            jpaPhoneCompany.setName(name);
        }

        jpaPhoneCompany.setUserAccount(Optional.ofNullable(jpaPhoneCompany.getUserAccount()).orElse(new UserAccount()));
        if (accountBalance != null) {
            jpaPhoneCompany.getUserAccount().setAccountBalance(accountBalance);
        }

        return jpaPhoneCompany;
    }

}
