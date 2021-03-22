package no.hvl.dat251.gr9.lopbackend.entities.dto;

import lombok.Data;
import no.hvl.dat251.gr9.lopbackend.entities.Race;


import java.util.GregorianCalendar;
import java.util.List;

@Data
public class EventDTO {
    private String name;
    private GregorianCalendar eventStart;
    private String generalInfo;
    private List<Race> races;

    public EventDTO(String name, GregorianCalendar eventStart, String generalInfo, List<Race> races) {
        this.name = name;
        this.eventStart = eventStart;
        this.generalInfo = generalInfo;
        this.races = races;
    }
}
