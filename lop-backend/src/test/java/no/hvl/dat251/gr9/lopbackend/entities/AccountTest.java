package no.hvl.dat251.gr9.lopbackend.entities;

import no.hvl.dat251.gr9.lopbackend.entities.dao.AccountDAO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class AccountTest {

    @Test
     void accountGetSetName() {
        String email = "fossen.kenneth@gmail.com";
        var acc = new AccountDAO();
        acc.setUsername(email);
        assertEquals(email, acc.getUsername());
    }

    // this should fail if the password is hashed
    @Test
    void accountGetSetPassword() {
        String password = "nisse";
        var acc = new AccountDAO();
        acc.setPassword(password);
        assertEquals(password, acc.getPassword());
    }
}
