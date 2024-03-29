package no.hvl.dat251.gr9.lopbackend.entities.dto;


import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@RequiredArgsConstructor
public class SignUpFormDTO {
    @NotBlank(message = "Cannot be blank")
    private final String firstname;
    @NotBlank(message = "Cannot be blank")
    private final String lastname;

    @Past(message="Must be a past date") // :P
    private final LocalDate birthdate;
    @NotBlank(message = "Cannot be blank")
    private final String address;
    @NotBlank(message = "Cannot be blank")
    private final String city;

    @NotBlank(message = "Cannot be blank")
    @Email(message = "Not a valid email address")
    @Size(max = 60, message = "Email can't be longer than 60 characters")
    private final String email;

    @NotBlank(message = "Cannot be blank")
    @Size(min = 8, max = 60, message = "Password must be between 8 and 60 characters")
    private final String password;
}
