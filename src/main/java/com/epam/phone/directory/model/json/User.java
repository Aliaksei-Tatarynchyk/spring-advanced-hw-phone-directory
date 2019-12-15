package com.epam.phone.directory.model.json;

import static org.springframework.util.CollectionUtils.isEmpty;

import java.util.List;
import java.util.Optional;

import com.epam.phone.directory.repository.UserRepository;

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

    public com.epam.phone.directory.model.db.User toJPA(UserRepository userRepository) {
        com.epam.phone.directory.model.db.User jpaUser = Optional.ofNullable(id).map(
                id -> userRepository.findById(id)
                        .orElse(new com.epam.phone.directory.model.db.User(id)))
                .orElse(new com.epam.phone.directory.model.db.User());

        if (firstName != null) {
            jpaUser.setFirstName(firstName);
        }
        if (lastName != null) {
            jpaUser.setLastName(lastName);
        }
        if (username != null) {
            jpaUser.setUsername(username);
        }
        if (password != null) {
            jpaUser.setPassword(password);
        }
        if (!isEmpty(roles)) {
            jpaUser.setRoles(roles);
        }

        return jpaUser;
    }
}
