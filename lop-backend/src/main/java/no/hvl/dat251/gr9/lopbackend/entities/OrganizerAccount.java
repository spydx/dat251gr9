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
public class OrganizerAccount implements Account{

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
    private OrganizerProfile profile;

    public OrganizerAccount(String id, @Email String email, String password, Set<Roles> roles, OrganizerProfile profile) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.roles = roles;
        this.profile = profile;
    }

    public OrganizerAccount() {}
}