package no.hvl.dat251.gr9.lopbackend.entities.dto;

import no.hvl.dat251.gr9.lopbackend.entities.Contacts;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrganizerDTOTest {

    @Test
    void organizerDTOGetSetTest(){
        var organizer = new OrganizerAccountDTO();

        String name = "testname";
        String address = "somewhere";
        String email = "test@test.test";
        String password = "testpass";

        List<Contacts> contacts = new ArrayList<>();
        var contact = new Contacts("testcontact", "test@contact.test", "12344678");
        contacts.add(contact);

        organizer.setOrganizerName(name);
        organizer.setAddress(address);
        organizer.setEmail(email);
        organizer.setPassword(password);
        organizer.setContacts(contacts);

        assertEquals(name, organizer.getOrganizerName());
        assertEquals(address, organizer.getAddress());
        assertEquals(email, organizer.getEmail());
        assertEquals(password, organizer.getPassword());
        assertEquals(contacts, organizer.getContacts());

    }
}
