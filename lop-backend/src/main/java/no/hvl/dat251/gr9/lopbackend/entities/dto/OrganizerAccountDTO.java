package no.hvl.dat251.gr9.lopbackend.entities.dto;

import lombok.Data;
import no.hvl.dat251.gr9.lopbackend.entities.Contacts;

import java.util.List;

@Data
public class OrganizerAccountDTO {

    private String organizerName;
    private String address;
    private List<Contacts> contacts;

    public OrganizerAccountDTO(String organizerName, String address, List<Contacts> contacts) {
        this.organizerName = organizerName;
        this.address = address;
        this.contacts = contacts;
    }
}
