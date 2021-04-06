package no.hvl.dat251.gr9.lopbackend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
public class UserProfile implements Serializable{

    @Id @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid2")
    private String id;

    private String firstname;
    private String lastname;
    private Date birthdate;
    private String address;
    private String city;

    @JsonIgnore
    @OneToOne(fetch = FetchType.EAGER)
    private UserAccount account;

    @Override
    public String toString() {
        return "UserProfile{" +
                "id='" + id + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", birthdate'" + birthdate + '\'' +
                ", address'" + address + '\'' +
                ", city'" + city + '\'' +
                ", account=" + account +
                '}';
    }
}
