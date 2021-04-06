package no.hvl.dat251.gr9.lopbackend.services;

import no.hvl.dat251.gr9.lopbackend.entities.Event;
import no.hvl.dat251.gr9.lopbackend.entities.Race;
import no.hvl.dat251.gr9.lopbackend.entities.dao.EventDAO;
import no.hvl.dat251.gr9.lopbackend.entities.dao.RaceDAO;
import no.hvl.dat251.gr9.lopbackend.entities.dto.EventDTO;
import no.hvl.dat251.gr9.lopbackend.entities.dto.RaceDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class RaceService {

    @Autowired
    private RaceDAO raceStorage;

    @Autowired
    private EventDAO eventStorage;

    @Autowired
    private EventService eventService;

    private final Logger logger = LoggerFactory.getLogger(EventService.class);

    public Optional<List<Race>> getAllRaces() { return Optional.of(raceStorage.findAll()); }

    public Optional<Race> getRace(String id) { return raceStorage.findById(id); }

    public Optional<List<Race>> getRacesFromEvent(String eventId) {
        var event = eventStorage.findById(eventId);
        if(event.isPresent()) {
            return event.map(Event::getRaces);
        }
        logger.error("Cant find race in current event.");
        return Optional.empty();
    }

    @Transactional
    public Optional<Race> add(RaceDTO newRace, String eventId) {
        var event = eventStorage.findById(eventId);
        if(event.isPresent()) {
            var race = new Race(newRace.getDistance(),
                    newRace.getStartTime(),
                    newRace.getElevation(),
                    newRace.getHillRun(),
                    newRace.getChildren(),
                    newRace.getWomenOnly(),
                    newRace.getRelay(),
                    newRace.getMultiSport(),
                    newRace.getObstacleRun(),
                    newRace.getInfo());

            var r = raceStorage.save(race);
            Event ev = event.get();
            EventDTO newEvent = new EventDTO(
                    ev.getName(),
                    ev.getEventStart(),
                    ev.getGeneralInfo(),
                    ev.getRaces(),
                    ev.getContacts(),
                    ev.getLocation()
            );
            newEvent.getRaces().add(r);
            eventService.updateEvent(eventId, newEvent);
            return Optional.of(r);
        }
        logger.error("Could not add the race to the event: ", eventId);
        return Optional.empty();
    }
}
