package no.hvl.dat251.gr9.lopbackend.integration.location;

import no.hvl.dat251.gr9.lopbackend.entities.Location;
import no.hvl.dat251.gr9.lopbackend.entities.dao.LocationDAO;
import no.hvl.dat251.gr9.lopbackend.integration.IntegrationTestContextConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test")
@Import(IntegrationTestContextConfiguration.class)
public class LocationDAOIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private LocationDAO locationStorage;

    private int numberOfLocations = 0;
    @BeforeEach
    void setUp(){
        var loc1 = new Location();
        loc1.setCounty("county1");
        loc1.setMunicipality("municipality1");
        loc1.setPlace("place1");
        loc1.setLatitude(30.0);
        loc1.setLongitude(45.0);
        entityManager.persist(loc1);
        numberOfLocations += 2;

        var loc2 = new Location();
        loc2.setCounty("county2");
        loc2.setMunicipality("municipality2");
        loc2.setPlace("place2");
        loc2.setLatitude(50.5);
        loc2.setLongitude(60.6);
        entityManager.persist(loc2);
        numberOfLocations += 2;

    }

    @Test
    void persistLocation(){
        var loc = new Location();
        loc.setCounty("testcounty");
        loc.setMunicipality("testmunicipality");
        loc.setPlace("testplace");
        loc.setLatitude(20.0);
        loc.setLongitude(10.0);
        entityManager.persist(loc);

        var found = locationStorage.findById(loc.getId());
        assertEquals(loc.getLatitude(), found.get().getLatitude());
        assertEquals(loc.getLongitude(), found.get().getLongitude());
    }

    @Test
    void findAllLocations(){
        var res = locationStorage.findAll();
        assertEquals(numberOfLocations, res.size());
    }

    @Test
    void findByCountyMunicipalityPlace(){
        var res = locationStorage.findByCountyAndMunicipalityAndPlace
                ("county1", "municipality1", "place1");
        assertEquals(30.0, res.get().getLatitude());
        assertEquals(45.0, res.get().getLongitude());
    }

    @Test
    void findByCoordinate(){
        var res= locationStorage.findByLatitudeAndLongitude(50.5, 60.6);
        assertEquals("county2", res.get().getCounty());
        assertEquals("municipality2", res.get().getMunicipality());
        assertEquals("place2",res.get().getPlace());
    }
}
