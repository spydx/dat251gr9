package no.hvl.dat251.gr9.lopbackend.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrganizerAccountTest {

    @Test
    void organizerAccountGetSetName() {
        String email = "fossen.kenneth@gmail.com";
        var acc = new OrganizerAccount();
        acc.setEmail(email);
        assertEquals(email, acc.getEmail());
    }


    @Test
    void organizerAccountGetSetPassword() {
        String password = "nisse";
        var acc = new OrganizerAccount();
        acc.setPassword(password);
        assertEquals(password, acc.getPassword());
    }
}
