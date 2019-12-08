package com.epam.phone.directory.service.security;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.epam.phone.directory.model.db.User;
import com.epam.phone.directory.service.UserService;
import com.google.common.collect.Iterables;

@Service
@Qualifier("userDetailsService")
public class H2UserDetailsService implements UserDetailsService {

    UserService userService;

    public H2UserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Username " + username + " is not found in UserRepository");
        }

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(Iterables.toArray(user.getRoles(), String.class))
                .build();
    }
}
