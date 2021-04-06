package no.hvl.dat251.gr9.lopbackend.entities.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ContactsDTOTest {

    @Test
    public void contactsDTOGetSetTest(){
        var contact = new ContactsDTO();

        var email = "testemail@test.no";
        var name = "test testesen";
        var phone = "2124142412412";

        contact.setEmail(email);
        contact.setName(name);
        contact.setPhone(phone);

        assertEquals(email, contact.getEmail());
        assertEquals(name, contact.getName());
        assertEquals(phone, contact.getPhone());
    }
}
