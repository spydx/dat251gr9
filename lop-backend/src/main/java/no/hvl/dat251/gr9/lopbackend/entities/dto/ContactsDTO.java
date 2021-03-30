package no.hvl.dat251.gr9.lopbackend.entities.dto;

import lombok.Data;

@Data
public class ContactsDTO {
    private String name;
    private String email;
    private int phone;

    public ContactsDTO(String name, String email, int phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }
}
