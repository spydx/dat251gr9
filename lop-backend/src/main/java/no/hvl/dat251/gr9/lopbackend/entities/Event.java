package no.hvl.dat251.gr9.lopbackend.entities;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
public class Event {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid2")
    private String id;

    private String name;
    private LocalDate eventStart;
    private String generalInfo;

    @OneToMany
    private List<Race> races;

    @ManyToMany
    private List<Contacts> contacts;


    public Event(String name, LocalDate eventStart, String generalInfo) {
        this.name = name;
        this.eventStart = eventStart;
        this.generalInfo = generalInfo;
    }

    public Event() {

    }
}
