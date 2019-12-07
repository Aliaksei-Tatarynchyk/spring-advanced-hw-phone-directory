package com.epam.phone.directory.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@Profile(value = {"dev", "default"}) // to enable security only for live application and disable it for tests
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user").password("{noop}user").roles("REGISTERED_USER").and()
                .withUser("admin").password("{noop}admin").roles("REGISTERED_USER", "BOOKING_MANAGER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/").permitAll();
        http.authorizeRequests().antMatchers("/users/current").hasAnyRole("REGISTERED_USER").and().formLogin();
        http.authorizeRequests().antMatchers("/**").hasAnyRole("BOOKING_MANAGER").and().formLogin();
        allowAnonymousAccessToH2WebConsole(http);
    }

    // add these lines to use H2 web console
    private void allowAnonymousAccessToH2WebConsole(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/h2-console/**").anonymous()
                .and().csrf().ignoringAntMatchers("/h2-console/**");
        http.headers().frameOptions().sameOrigin();
    }

    @Override
    public void configure(WebSecurity web) {
    }
}
