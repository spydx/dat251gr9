package no.hvl.dat251.gr9.lopbackend.entities.dto;

import lombok.Data;
import no.hvl.dat251.gr9.lopbackend.entities.*;
import java.time.LocalDate;
import java.util.List;

@Data
public class EventDTO {
    private String name;
    private LocalDate eventStart;
    private String generalInfo;
    private List<Race> races;
    private List<Contacts> contacts;
    private LocationDTO location;
    private OrganizerProfile organizer;

    public EventDTO(String name, LocalDate eventStart, String generalInfo, List<Race> races, List<Contacts> contacts, LocationDTO location, OrganizerProfile organizer) {
        this.name = name;
        this.eventStart = eventStart;
        this.generalInfo = generalInfo;
        this.races = races;
        this.contacts = contacts;
        this.location = location;
        this.organizer = organizer;
    }
}
