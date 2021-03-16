package no.hvl.dat251.gr9.lopbackend.entities.dto;


import lombok.Data;

@Data
public class AccountDTO {

    private String firstname;
    private String lastname;
    private String email;
    private String password;

    public AccountDTO(String firstname, String lastname, String email, String password) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
    }
}
