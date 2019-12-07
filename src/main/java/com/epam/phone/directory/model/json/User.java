package com.epam.phone.directory.model.json;

import java.util.List;

import com.google.common.base.Joiner;

public class User {
    Long id;
    String firstName;
    String lastName;
    String password;
    List<String> roles;

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword() {
        return password;
    }

    public List<String> getRoles() {
        return roles;
    }

    public com.epam.phone.directory.model.db.User toJPA() {
        com.epam.phone.directory.model.db.User.Builder builder = com.epam.phone.directory.model.db.User.newBuilder()
                .withId(this.id)
                .withFirstName(this.firstName)
                .withLastName(this.lastName)
                .withPassword(this.password);
        if (roles != null) {
            builder.withRoles(Joiner.on(',').join(this.roles));
        }
        return builder.build();
    }
}
