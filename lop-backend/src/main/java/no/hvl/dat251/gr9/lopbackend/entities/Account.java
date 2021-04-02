package no.hvl.dat251.gr9.lopbackend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Account {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid2")
    private String id;


    @Column(unique = true)
    @Email
    private String email;

    @JsonIgnore
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Roles> roles = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL)
    private Profile profile;

    public Account(String email){
        this.email = email;
    }

    public Account(){

    }

    @Override
    public String toString() {
        return "Profile{" +
                "id='" + id + '\'' +
                ", email'" + email + '\'' +
                ", password='" + password + '\'' +
                ", roles='" + roles + '\'' +
                ", profile'" + profile.getId() + '\'' +
                '}';
    }
}
