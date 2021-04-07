package no.hvl.dat251.gr9.lopbackend.integration;


import no.hvl.dat251.gr9.lopbackend.entities.OrganizerProfile;
import no.hvl.dat251.gr9.lopbackend.services.*;
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

    @Bean
    public EventService eventService() { return new EventService();}

    @Bean
    public RaceService raceService() { return new RaceService();}

    @Bean
    public ContactsService contactsService() { return new ContactsService(); }

    @Bean
    public LocationService locationService() {return new LocationService();}

    @Bean
    public OrganizerService organizerService() {return new OrganizerService(); }
}
