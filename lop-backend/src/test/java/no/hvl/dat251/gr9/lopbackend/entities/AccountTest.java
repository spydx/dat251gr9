package no.hvl.dat251.gr9.lopbackend.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class AccountTest {

    @Test
     void accountGetSetName() {
        String email = "fossen.kenneth@gmail.com";
        var acc = new Account();
        acc.setEmail(email);
        assertEquals(email, acc.getEmail());
    }

    // this should fail if the password is hashed
    @Test
    void accountGetSetPassword() {
        String password = "nisse";
        var acc = new Account();
        acc.setPassword(password);
        assertEquals(password, acc.getPassword());
    }
}
