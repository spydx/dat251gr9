package no.hvl.dat251.gr9.lopbackend.entities.dto;


import lombok.Data;

import java.time.LocalDate;

@Data
public class UserAccountDTO {

    private String firstname;
    private String lastname;
    private LocalDate birthdate;
    private String address;
    private String city;

    private String email;
    private String password;


    public UserAccountDTO(String firstname, String lastname, LocalDate birthdate, String address, String city, String email, String password) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthdate = birthdate;
        this.address = address;
        this.city = city;
        this.email = email;
        this.password = password;
    }
}
