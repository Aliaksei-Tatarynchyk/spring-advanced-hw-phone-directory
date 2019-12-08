package com.epam.phone.directory.model.json;

import static org.springframework.util.CollectionUtils.isEmpty;

import java.util.List;

public class User {
    Long id;
    String firstName;
    String lastName;
    String username;
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

    public String getUsername() {
        return username;
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
                .withUsername(this.username)
                .withPassword(this.password);
        if (!isEmpty(roles)) {
            builder.withRoles(this.roles);
        }
        return builder.build();
    }
}
