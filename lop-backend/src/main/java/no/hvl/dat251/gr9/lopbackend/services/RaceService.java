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


    public Optional<List<Race>> getAllRacesSortedByClosestLocationAscending(double latitude, double longitude){
        var races = raceStorage.findAll();
        Collections.sort(races, new Comparator<Race>(){
            @Override
            public int compare(Race r1, Race r2){
                if(r1.compareToDistToLoc(latitude, longitude, r2) < 0)return -1;
                else if(r1.compareToDistToLoc(latitude, longitude, r2) > 0)return 1;
                return 0;
            }
        });
        return Optional.of(races);
    }

    public Optional<List<Race>> getAllRacesSortedByClosestLocationDescending(double latitude, double longitude){
        var races = raceStorage.findAll();
        Collections.sort(races, new Comparator<Race>(){
            @Override
            public int compare(Race r1, Race r2){
                if(r1.compareToDistToLoc(latitude, longitude, r2) < 0)return 1;
                else if(r1.compareToDistToLoc(latitude, longitude, r2) > 0)return -1;
                return 0;
            }
        });
        return Optional.of(races);
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
            race.setLocation(event.get().getLocation());
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
}
