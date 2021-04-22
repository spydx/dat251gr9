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
import java.util.*;

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

    public Optional<List<Race>> getAllRacesSortedByParticipantsAscending(){
        return Optional.of(raceStorage.findByOrderByParticipantsAsc());
    }

    public Optional<List<Race>> getAllRacesSortedByParticipantsDescending(){
        return Optional.of(raceStorage.findByOrderByParticipantsDesc());
    }

    public Optional<List<Race>> getAllRacesSortedByDistanceAscending(){
        return Optional.of(raceStorage.findByOrderByDistanceAsc());
    }

    public Optional<List<Race>> getAllRacesSortedByDistanceDescending(){
        return Optional.of(raceStorage.findByOrderByDistanceDesc());
    }



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
            race.setParticipants(newRace.getParticipants());


            var r = raceStorage.save(race);
            Event ev = event.get();
            EventDTO newEvent = new EventDTO(
                    ev.getName(),
                    ev.getEventStart(),
                    ev.getGeneralInfo(),
                    ev.getRaces(),
                    ev.getContacts(),
                    ev.getLocation(),
                    ev.getOrganizer()
            );
            newEvent.getRaces().add(r);
            eventService.updateEvent(eventId, newEvent);
            return Optional.of(r);
        }
        logger.error("Could not add the race to the event: ", eventId);
        return Optional.empty();
    }

    public List<Race> filterOutRaces(List<Race> races, List<Race> otherRaces){
        List<Race> newList = new ArrayList<>();
        for(Race race : otherRaces){
            if(races.contains(race)){
                newList.add(race);
            }
        }
        return newList;
    }

    public Optional<List<Race>> getHillRunRaces(List<Race> races){
        return Optional.of(filterOutRaces(races, raceStorage.findByHillRunIsTrue()));
    }

    public Optional<List<Race>> getChildrenRaces(List<Race> races) {
        return Optional.of(filterOutRaces(races, raceStorage.findByChildrenIsTrue()));
    }

    public Optional<List<Race>> getWomenOnlyRaces(List<Race> races) {
        return Optional.of(filterOutRaces(races, raceStorage.findByWomenOnlyIsTrue()));
    }

    public Optional<List<Race>> getRelayRaces(List<Race> races) {
        return Optional.of(filterOutRaces(races, raceStorage.findByRelayIsTrue()));
    }

    public Optional<List<Race>> getMultiSportRaces(List<Race> races) {
        return Optional.of(filterOutRaces(races, raceStorage.findByMultiSportIsTrue()));
    }

    public Optional<List<Race>> getObstacleRunRaces(List<Race> races) {
        return Optional.of(filterOutRaces(races, raceStorage.findByObstacleRunIsTrue()));
    }

    public Optional<List<Race>> sortBy(List<Race> races, String sort) {
        switch (sort){
            case "DISTANCE_DESC":
                return Optional.of(filterOutRaces(races, raceStorage.findByOrderByDistanceDesc()));
            case "DISTANCE_ASC":
                return Optional.of(filterOutRaces(races, raceStorage.findByOrderByDistanceAsc()));
            case "ELEVATION_DESC":
                return Optional.of(filterOutRaces(races, raceStorage.findByOrderByElevationDesc()));
            case "ELEVATION_ASC":
                return Optional.of(filterOutRaces(races, raceStorage.findByOrderByElevationAsc()));
            case "PARTICIPANTS_DESC":
                return Optional.of(filterOutRaces(races, raceStorage.findByOrderByParticipantsDesc()));
            case "PARTICIPANTS_ASC":
                return Optional.of(filterOutRaces(races, raceStorage.findByOrderByParticipantsAsc()));
        }
        return Optional.of(races);
    }
}
