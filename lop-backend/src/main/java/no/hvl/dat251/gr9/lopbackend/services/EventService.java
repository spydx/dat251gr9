package no.hvl.dat251.gr9.lopbackend.services;

import no.hvl.dat251.gr9.lopbackend.entities.Event;
import no.hvl.dat251.gr9.lopbackend.entities.Location;
import no.hvl.dat251.gr9.lopbackend.entities.dao.EventDAO;
import no.hvl.dat251.gr9.lopbackend.entities.dao.LocationDAO;
import no.hvl.dat251.gr9.lopbackend.entities.dto.EventDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EventService {

    @Autowired
    private EventDAO eventStorage;

    @Autowired
    private LocationDAO locationDAO;

    private final Logger logger = LoggerFactory.getLogger(EventService.class);

    public Optional<List<Event>> getAllEvents() {
        return Optional.of(eventStorage.findAll());
    }

    public Optional<Event> getEvent(String id) { return eventStorage.findById(id); }

    public Optional<List<Event>> getEventsByTerm(String term){
        return Optional.of(eventStorage.findByNameContains(term));
    }

    public Optional<List<Event>> getEventsSortedByClosestLocationAscending(List<Event> events, double latitude, double longitude){
        Collections.sort(events, new Comparator<Event>(){
            @Override
            public int compare(Event e1, Event e2){
                if(e1.compareToDistToLoc(latitude, longitude, e2) < 0)return -1;
                else if(e1.compareToDistToLoc(latitude, longitude, e2) > 0)return 1;
                return 0;
            }
        });
        return Optional.of(events);
    }

    public List<Event> getAllEventsSortedByClosestLocationDescending(double latitude, double longitude){
        var events = eventStorage.findAll();
        Collections.sort(events, new Comparator<Event>(){
            @Override
            public int compare(Event e1, Event r2){
                if(e1.compareToDistToLoc(latitude, longitude, r2) < 0)return 1;
                else if(e1.compareToDistToLoc(latitude, longitude, r2) > 0)return -1;
                return 0;
            }
        });
        return events;
    }

    public Optional<List<Event>> sortBy (List<Event> events, String sort){
        switch (sort){
            case "EVENT_START_DESC":
                return Optional.of(filterOutEvents(events, eventStorage.findByOrderByEventStartDesc()));
            case "EVENT_START_ASC":
                return Optional.of(filterOutEvents(events, eventStorage.findByOrderByEventStartAsc()));
        }
        return Optional.of(events);
    }

    public List<Event> filterOutEvents(List<Event> events, List<Event> sortedEvents){
        List<Event> newList = new ArrayList<>();
        for(Event event : sortedEvents){
            if(events.contains(event)){
                newList.add(event);
            }
        }
        return newList;
    }


    public Optional<Event> add(EventDTO newEvent) {

        var location = new Location(
                newEvent.getLocation().getCounty(),
                newEvent.getLocation().getMunicipality(),
                newEvent.getLocation().getPlace(),
                newEvent.getLocation().getLatitude(),
                newEvent.getLocation().getLongitude()

        );

        var event = new Event(
                newEvent.getName(),
                newEvent.getEventStart(),
                newEvent.getGeneralInfo(),
                newEvent.getRaces(),
                newEvent.getContacts(),
                newEvent.getOrganizer(),
                location
        );
        var comp = eventStorage.save(event);
        return Optional.of(comp);

    }

    public Optional<Event> updateEvent(String id, EventDTO updated) {
        var event = eventStorage.findById(id);
        if(event.isPresent()) {

            locationDAO.deleteById(event.get().getLocation().getId());

            var location = new Location(
                    updated.getLocation().getCounty(),
                    updated.getLocation().getMunicipality(),
                    updated.getLocation().getPlace(),
                    updated.getLocation().getLatitude(),
                    updated.getLocation().getLongitude()
            );

            event.get().setName(updated.getName());
            event.get().setEventStart(updated.getEventStart());
            event.get().setGeneralInfo(updated.getGeneralInfo());
            event.get().setContacts(updated.getContacts());
            event.get().setRaces(updated.getRaces());
            event.get().setLocation(location);
            event.get().setOrganizer(updated.getOrganizer());

            return Optional.of(eventStorage.save(event.get()));
        }
        logger.error("Cant find event for {}", updated);

        return Optional.empty();
    }
}
