package com.epam.phone.directory.service;

import java.util.Collection;

import javax.annotation.Nullable;

import org.springframework.stereotype.Service;

import com.epam.phone.directory.model.db.User;
import com.epam.phone.directory.repository.UserRepository;

import io.micrometer.core.instrument.util.StringUtils;

@Service
public class UserService {

    final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Collection<User> getAllUsers() {
        return (Collection<User>) userRepository.findAll();
    }

    @Nullable
    public User getUserByUsername(@Nullable String username) {
        if (StringUtils.isBlank(username)) {
            return null;
        }

        return getAllUsers().stream()
                .filter(user -> username.equals(user.getUsername()))
                .findFirst().orElse(null);
    }

}
