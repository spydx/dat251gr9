package no.hvl.dat251.gr9.lopbackend.entities.dto;

import no.hvl.dat251.gr9.lopbackend.entities.Location;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LocationDTOTest {
    @Test
    public void locationGetSetTest(){
        var county = "Vestland";
        var municipality = "Sunnfjord";
        var place = "Førde";
        var latitude = 61.451931;
        var longitude = 5.843860;

        Location location = new Location();
        location.setCounty(county);
        location.setMunicipality(municipality);
        location.setPlace(place);
        location.setLatitude(latitude);
        location.setLongitude(longitude);

        assertEquals(county, location.getCounty());
        assertEquals(municipality, location.getMunicipality());
        assertEquals(place, location.getPlace());
        assertEquals(latitude, location.getLatitude());
        assertEquals(longitude, location.getLongitude());
    }
}
