package no.hvl.dat251.gr9.lopbackend.services;

import no.hvl.dat251.gr9.lopbackend.entities.Event;
import no.hvl.dat251.gr9.lopbackend.entities.Location;
import no.hvl.dat251.gr9.lopbackend.entities.dao.EventDAO;
import no.hvl.dat251.gr9.lopbackend.entities.dao.LocationDAO;
import no.hvl.dat251.gr9.lopbackend.entities.dto.EventDTO;
import no.hvl.dat251.gr9.lopbackend.entities.dto.LocationDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class LocationService {

    @Autowired
    private LocationDAO locationStorage;

    @Autowired
    private EventDAO eventStorage;

    @Autowired
    private EventService eventService;

    private final Logger logger = LoggerFactory.getLogger(LocationService.class);

    public Optional<List<Location>> getAllLocations(){
        return Optional.of(locationStorage.findAll());
    }

    public Optional<Location> getLocationByID(String id){
        return locationStorage.findById(id);
    }

    public Optional<Location> getLocationByName(String county, String municipality, String place){
        return locationStorage.findByCountyAndMunicipalityAndPlace(county, municipality, place);
    }

    public Optional<Location> getLocationByCoordinates(double latitude, double longitude){
        return locationStorage.findByLatitudeAndLongitude(latitude, longitude);
    }

    @Transactional
    public Optional<Location> add(LocationDTO newLocation, String eventId){
        var event = eventStorage.findById(eventId);
        if(event.isPresent()){
            var location = new Location(
                    newLocation.getCounty(),
                    newLocation.getMunicipality(),
                    newLocation.getPlace(),
                    newLocation.getLatitude(),
                    newLocation.getLongitude()
            );
            var loc = locationStorage.save(location);
            Event ev = event.get();
            EventDTO updatedEvent = new EventDTO(
                    ev.getName(),
                    ev.getEventStart(),
                    ev.getGeneralInfo(),
                    ev.getRaces(),
                    ev.getContacts(),
                    ev.getLocation(),
                    ev.getOrganizer()
            );
            updatedEvent.setLocation(loc);
            eventService.updateEvent(eventId, updatedEvent);
            return Optional.of(loc);
        }
        logger.error("Could not add location to event: {}", eventId);
        return Optional.empty();
    }

}
