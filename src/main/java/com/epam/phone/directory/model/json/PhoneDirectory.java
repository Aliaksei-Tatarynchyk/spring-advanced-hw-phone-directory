package com.epam.phone.directory.model.json;

import java.util.Collection;

public class PhoneDirectory {
    Collection<User> users;
    Collection<PhoneCompany> phoneCompanies;
    Collection<PhoneNumber> phoneNumbers;

    public Collection<User> getUsers() {
        return users;
    }

    public void setUsers(Collection<User> users) {
        this.users = users;
    }

    public Collection<PhoneCompany> getPhoneCompanies() {
        return phoneCompanies;
    }

    public void setPhoneCompanies(Collection<PhoneCompany> phoneCompanies) {
        this.phoneCompanies = phoneCompanies;
    }

    public Collection<PhoneNumber> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(Collection<PhoneNumber> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }
}
