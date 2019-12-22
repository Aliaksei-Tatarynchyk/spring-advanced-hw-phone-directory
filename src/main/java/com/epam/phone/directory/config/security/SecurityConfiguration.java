package com.epam.phone.directory.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * https://docs.spring.io/spring-security/site/docs/current/reference/htmlsingle/#multiple-httpsecurity
 */
@EnableWebSecurity
@Profile(value = {"dev", "default"}) // to enable security only for live application and disable it for tests
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        auth.authenticationProvider(daoAuthenticationProvider())
                .inMemoryAuthentication()
                    // roles should not have prefix ROLE_ because this prefix is added automatically
                    .withUser("admin").password(encoder.encode("admin")).roles("REGISTERED_USER", "BOOKING_MANAGER", "REST_USER");
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(getUserDetailsService());
        return authenticationProvider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // add these lines to use H2 web console
        http.authorizeRequests()
                .antMatchers("/h2-console/**").permitAll()
                .and().csrf().ignoringAntMatchers("/h2-console/**");
        http.headers().frameOptions().sameOrigin();
    }

    @Configuration
    @Order(1)
    @Profile(value = {"dev", "default"})
    public static class FormLoginWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests()
                    .antMatchers("/").permitAll()
                    .antMatchers("/static/**").permitAll()
                    // roles should not have prefix ROLE_ because this prefix is added automatically
                    .antMatchers("/users/current").hasAnyRole("REGISTERED_USER")
                    .antMatchers("/**").hasAnyRole("BOOKING_MANAGER")
                    .and().formLogin().loginPage("/login").permitAll()
                    .and().exceptionHandling().accessDeniedPage("/accessDenied")
                    .and().logout().permitAll()
                    .and().csrf()
                    .and().rememberMe();
        }

        @Override
        public void configure(WebSecurity web) {
            web.ignoring().antMatchers("/api/**", "/h2-console/**");
        }
    }

    @Configuration
    @Order(2)
    @Profile(value = {"dev", "default"})
    public static class ApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests()
                    .antMatchers( "/api/**").hasAnyRole("REST_USER")
                    .and().httpBasic();
        }
    }

    public UserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

    @Autowired
    public void setUserDetailsService(@Qualifier("userDetailsService") UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
}
