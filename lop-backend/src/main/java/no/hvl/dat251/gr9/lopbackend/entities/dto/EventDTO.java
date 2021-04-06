package no.hvl.dat251.gr9.lopbackend.entities.dto;

import lombok.Data;
import no.hvl.dat251.gr9.lopbackend.entities.Contacts;
import no.hvl.dat251.gr9.lopbackend.entities.Race;


import java.time.LocalDate;
import java.util.List;

@Data
public class EventDTO {
    private String name;
    private LocalDate eventStart;
    private String generalInfo;
    private List<Race> races;
    private List<Contacts> contacts;

    public EventDTO(String name, LocalDate eventStart, String generalInfo, List<Race> races, List<Contacts> contacts) {
        this.name = name;
        this.eventStart = eventStart;
        this.generalInfo = generalInfo;
        this.races = races;
        this.contacts = contacts;
    }
}
