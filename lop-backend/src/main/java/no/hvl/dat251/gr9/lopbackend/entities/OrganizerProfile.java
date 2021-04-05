package no.hvl.dat251.gr9.lopbackend.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
public class OrganizerProfile implements Serializable{

    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid2")
    @JsonIgnore
    private String id;

    private String organizerName;


    @JsonIgnore
    @OneToOne(fetch = FetchType.EAGER)
    private OrganizerAccount account;


    @Override
    public String toString() {
        return "OrganizerProfile{" +
                "id='" + id + '\'' +
                ", organizerName='" + organizerName + '\'' +
                ", account=" + account +
                '}';
    }
}
