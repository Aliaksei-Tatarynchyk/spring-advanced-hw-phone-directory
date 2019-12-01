package com.epam.phone.directory.model.json;

public class User {
    Long id;
    String firstName;
    String lastName;

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

    public com.epam.phone.directory.model.db.User toJPA() {
        return com.epam.phone.directory.model.db.User.newBuilder()
                .withFirstName(this.firstName)
                .withLastName(this.lastName)
                .withId(this.id)
                .build();
    }
}
