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

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Contacts> contacts;

    @OneToOne
    private OrganizerProfile organizer;

    @ManyToOne(cascade = CascadeType.ALL)
    private Location location;


    public Event(String name, LocalDate eventStart, String generalInfo, List<Race> races, List<Contacts> contacts, OrganizerProfile organizer, Location location) {
        this.name = name;
        this.eventStart = eventStart;
        this.generalInfo = generalInfo;
        this.races = races;
        this.contacts = contacts;
        this.organizer = organizer;
        this.location = location;
    }

    public Event() {}
}
