package no.hvl.dat251.gr9.lopbackend.services;

import no.hvl.dat251.gr9.lopbackend.entities.Location;
import no.hvl.dat251.gr9.lopbackend.entities.dao.LocationDAO;
import no.hvl.dat251.gr9.lopbackend.entities.dto.LocationDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LocationService {

    @Autowired
    private LocationDAO locationStorage;

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

    public Optional<Location> add(LocationDTO newLocation){
        var location = new Location(newLocation.getCounty(), newLocation.getMunicipality(), newLocation.getPlace(),
                newLocation.getLatitude(), newLocation.getLatitude());
        var loc = locationStorage.save(location);
        return Optional.of(loc);
    }

}
