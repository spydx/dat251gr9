package no.hvl.dat251.gr9.lopbackend.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ContactsTest {

    @Test
    public void contactsGetSetTest(){
        var contact = new Contacts();

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
