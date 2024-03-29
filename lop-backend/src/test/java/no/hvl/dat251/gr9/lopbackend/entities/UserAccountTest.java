package no.hvl.dat251.gr9.lopbackend.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class UserAccountTest {

    @Test
     void accountGetSetName() {
        String email = "fossen.kenneth@gmail.com";
        var acc = new UserAccount();
        acc.setEmail(email);
        assertEquals(email, acc.getEmail());
    }


    @Test
    void accountGetSetPassword() {
        String password = "nisse";
        var acc = new UserAccount();
        acc.setPassword(password);
        assertEquals(password, acc.getPassword());
    }
}
