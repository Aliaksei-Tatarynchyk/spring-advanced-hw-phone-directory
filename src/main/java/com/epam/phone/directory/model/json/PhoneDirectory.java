package com.epam.phone.directory.model.json;

import java.util.Collection;

public class PhoneDirectory {
    Collection<User> users;
    Collection<PhoneCompany> phoneCompanies;
    Collection<PhoneNumber> phoneNumbers;

    public Collection<User> getUsers() {
        return users;
    }

    public Collection<PhoneCompany> getPhoneCompanies() {
        return phoneCompanies;
    }

    public Collection<PhoneNumber> getPhoneNumbers() {
        return phoneNumbers;
    }

}
