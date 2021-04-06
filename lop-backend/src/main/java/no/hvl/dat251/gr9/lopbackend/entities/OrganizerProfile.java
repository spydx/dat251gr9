package no.hvl.dat251.gr9.lopbackend.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
public class OrganizerProfile implements Serializable{

    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid2")
    @JsonIgnore
    private String id;

    private String organizerName;

    private String Address;

    @JsonIgnore
    @OneToMany
    private List<Contacts> contact;


    @JsonIgnore
    @OneToOne(fetch = FetchType.EAGER)
    private OrganizerAccount account;


}
