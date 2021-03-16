package no.hvl.dat251.gr9.lopbackend.entities;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class Event {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid2")
    private String id;

    private String name;
    private Date eventStart;
    private String generalInfo;

    @OneToMany
    private List<Race> races;

    public Event(String name, Date eventStart, String generalInfo) {
        this.name = name;
        this.eventStart = eventStart;
        this.generalInfo = generalInfo;
    }

    public Event() {

    }
}
