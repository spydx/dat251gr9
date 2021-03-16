package no.hvl.dat251.gr9.lopbackend.integration;

import no.hvl.dat251.gr9.lopbackend.services.SetupService;
import no.hvl.dat251.gr9.lopbackend.services.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

public class IntegrationTestContextConfiguration {

    @Bean
    public UserService userService() {
        return new UserService();
    }

    @Bean
    public SetupService setupService() {
        return new SetupService();
    }

    @Bean
    public PasswordEncoder scryptEncoder() {
        return new SCryptPasswordEncoder();
    }
}
