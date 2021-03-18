package no.hvl.dat251.gr9.lopbackend.entities.dto;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PasswordDTOTest {

    @Test
    void passwordGetSetPassword() {
        String password = "SuperPassword";
        var passwd = new PasswordDTO();
        passwd.setPassword(password);

        assertEquals(password, passwd.getPassword());
    }

    @Test
    void passwordGetSetAccount() {
        String email = "fossen.kenneth@gmail.com";
        var password = new PasswordDTO();
        password.setEmail(email);
        assertEquals(email, password.getEmail());
    }
}
