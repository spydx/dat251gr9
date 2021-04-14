package no.hvl.dat251.gr9.lopbackend.entities.dto;

import lombok.Data;
import no.hvl.dat251.gr9.lopbackend.entities.Contacts;

import java.util.List;

@Data
public class OrganizerAccountDTO {

    private String organizerName;
    private String address;
    private List<Contacts> contacts;
    private String email;
    private String password;

    public OrganizerAccountDTO(String organizerName, String address, List<Contacts> contacts, String email, String password) {
        this.organizerName = organizerName;
        this.address = address;
        this.contacts = contacts;
        this.email = email;
        this.password = password;
    }

    public OrganizerAccountDTO() {
    }
}
