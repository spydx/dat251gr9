package no.hvl.dat251.gr9.lopbackend.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    private String firstname;
    private String lastname;
    private Date birthdate;
    private String address;
    private String city;

    @OneToOne(fetch = FetchType.EAGER)
    private Account account;
}
