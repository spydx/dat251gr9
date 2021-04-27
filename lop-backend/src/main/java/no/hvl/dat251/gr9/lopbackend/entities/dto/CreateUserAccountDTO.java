package no.hvl.dat251.gr9.lopbackend.entities.dto;


import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
public class CreateUserAccountDTO {

    private String firstname;
    private String lastname;
    private LocalDate birthdate;
    private String address;
    private String city;

    @NotBlank(message = "Email can't be blank")
    @Email(message = "Must provide a valid email")
    @Size(max = 60,message = "Email can't be longer than 60 characters")
    private String email;

    @NotBlank(message = "Password can't be blank")
    @Size(min = 6, max = 60, message = "Password must be between 6 and 60 characters")
    private String password;


    public CreateUserAccountDTO(String firstname,
                                String lastname,
                                LocalDate birthdate,
                                String address,
                                String city,
                                String email,
                                String password) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthdate = birthdate;
        this.address = address;
        this.city = city;
        this.email = email;
        this.password = password;
    }
}
