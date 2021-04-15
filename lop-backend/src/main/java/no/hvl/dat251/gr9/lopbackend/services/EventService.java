package no.hvl.dat251.gr9.lopbackend.services;

import no.hvl.dat251.gr9.lopbackend.entities.Event;
import no.hvl.dat251.gr9.lopbackend.entities.Race;
import no.hvl.dat251.gr9.lopbackend.entities.dao.EventDAO;
import no.hvl.dat251.gr9.lopbackend.entities.dto.EventDTO;
import no.hvl.dat251.gr9.lopbackend.entities.dto.RaceDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    @Autowired
    private EventDAO eventStorage;

    private final Logger logger = LoggerFactory.getLogger(EventService.class);

    public Optional<List<Event>> getAllEvents() {
        return Optional.of(eventStorage.findAll());
    }

    public Optional<Event> getEvent(String id) { return eventStorage.findById(id); }

    public Optional<Event> add(EventDTO newComp) {
        var competition = new Event(
                newComp.getName(),
                newComp.getEventStart(),
                newComp.getGeneralInfo(),
                newComp.getRaces(),
                newComp.getContacts(),
                newComp.getOrganizer(),
                newComp.getLocation()
        );
        var comp = eventStorage.save(competition);
        return Optional.of(comp);

    }

    public Optional<Event> updateEvent(String id, EventDTO updated) {
        var competition = eventStorage.findById(id);
        if(competition.isPresent()) {
            competition.get().setName(updated.getName());
            competition.get().setEventStart(updated.getEventStart());
            competition.get().setGeneralInfo(updated.getGeneralInfo());
            competition.get().setContacts(updated.getContacts());
            competition.get().setRaces(updated.getRaces());
            competition.get().setLocation(updated.getLocation());
            competition.get().setOrganizer(updated.getOrganizer());

            return Optional.of(eventStorage.save(competition.get()));
        }
        logger.error("Cant find competition for {}", updated);

        return Optional.empty();
    }
}
