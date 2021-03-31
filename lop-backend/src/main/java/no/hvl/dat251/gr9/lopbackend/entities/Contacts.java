package no.hvl.dat251.gr9.lopbackend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Email;

@Entity
@Data
public class Contacts {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid2")
    @JsonIgnore
    private String id;

    private String name;

    @Email
    private String email;

    private String phone;

    public Contacts(String name, @Email String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public Contacts() {
    }
}
