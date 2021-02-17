package no.hvl.dat251.gr9.lopbackend.entities.dao;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class AccountDAO {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    //email
    private String username;
    private String password;

}
