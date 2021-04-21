package no.hvl.dat251.gr9.lopbackend.integration.race;


import no.hvl.dat251.gr9.lopbackend.entities.Location;
import no.hvl.dat251.gr9.lopbackend.entities.Race;
import no.hvl.dat251.gr9.lopbackend.entities.dao.RaceDAO;
import no.hvl.dat251.gr9.lopbackend.geocoding.APIRequest;
import no.hvl.dat251.gr9.lopbackend.integration.IntegrationTestContextConfiguration;
import no.hvl.dat251.gr9.lopbackend.services.RaceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import java.time.LocalTime;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test")
@Import(IntegrationTestContextConfiguration.class)
public class raceSearchTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private RaceDAO raceStorage;

    @Autowired
    private RaceService raceService;


    @BeforeEach
    void setUp(){
        var race1  = new Race(21.0f , LocalTime.now(), 1.0f, true, true, true,
                true, true, true, "test");
        var race2  = new Race(5.0f , LocalTime.now(), 1.0f, true, true, true,
                true, true, true, "test");
        var race3  = new Race(10.0f , LocalTime.now(), 1.0f, true, true, true,
                true, true, true, "test");

        race1.setParticipants(2);
        race2.setParticipants(1);
        race3.setParticipants(3);

        var race1Loc = new Location("test", "test", "test", 45, 45);
        var race2Loc = new Location("test", "test", "test", 10, 10);
        var race3Loc = new Location("test", "test", "test", 100, 80);

        entityManager.persist(race1Loc);
        entityManager.persist(race2Loc);
        entityManager.persist(race3Loc);

       /* race1.setLocation(race1Loc);
        race2.setLocation(race2Loc);
        race3.setLocation(race3Loc);
*/
        entityManager.persist(race1);
        entityManager.persist(race2);
        entityManager.persist(race3);
    }

    @Test
     void sortByDistanceAscending(){
        var races = raceStorage.findByOrderByDistanceAsc();
        for(int i = 1; i < races.size(); i++){
            assertTrue(races.get(i-1).getDistance() <= races.get(i).getDistance());
        }
    }

    @Test
    void sortByDistanceDescending(){
        var races = raceStorage.findByOrderByDistanceDesc();
        for(int i = 1; i < races.size(); i++){
            assertTrue(races.get(i-1).getDistance() >= races.get(i).getDistance());
        }
    }

    @Test
    void sortByParticipantsAscending(){
        var races = raceStorage.findByOrderByParticipantsAsc();
        for(int i = 1; i < races.size(); i++){
            assertTrue(races.get(i-1).getParticipants() <= races.get(i).getParticipants());
        }
    }

    @Test
    void sortByParticipantsDescending(){
        var races = raceStorage.findByOrderByParticipantsDesc();
        for(int i = 1; i < races.size(); i++){
            assertTrue(races.get(i-1).getParticipants() >= races.get(i).getParticipants());
        }
    }

    /*@Test
    void sortByClosestLocationAscending(){
        double latitude = 0;
        double longitude = 0;
        var racesOptional = raceService.getAllRacesSortedByClosestLocationAscending(latitude, longitude);
        if(racesOptional.isPresent()){
            var races = racesOptional.get();
            for(int i = 1; i < races.size(); i++){
                Location loc1 = races.get(i-1).getLocation();
                Location loc2 = races.get(i).getLocation();
                double distLoc1 = loc1.getDistanceBetweenLocations(latitude, longitude);
                double distLoc2 = loc2.getDistanceBetweenLocations(latitude, longitude);
                assertTrue(distLoc1 <= distLoc2);
            }
        }
    }

    @Test
    void sortByClosestLocationDescending(){
        double latitude = 0;
        double longitude = 0;
        var racesOptional = raceService.getAllRacesSortedByClosestLocationDescending(latitude, longitude);
        if(racesOptional.isPresent()){
            var races = racesOptional.get();
            for(int i = 1; i < races.size(); i++){
                Location loc1 = races.get(i-1).getLocation();
                Location loc2 = races.get(i).getLocation();
                double distLoc1 = loc1.getDistanceBetweenLocations(latitude, longitude);
                double distLoc2 = loc2.getDistanceBetweenLocations(latitude, longitude);
                assertTrue(distLoc1 >= distLoc2);
            }
        }
    }

    @Test
    void searchByLocationAsStringAndSortByClosestDistanceAsc() throws ExecutionException, InterruptedException {
        double[] latitudeAndLongitude = APIRequest.getLatitudeAndLongitude("Bergen Vestland");
        double latitude = latitudeAndLongitude[0];
        double longitude = latitudeAndLongitude[1];
        double latitude2 = 60.39299;
        double longitude2 = 5.32415;
        var racesOptional = raceService.getAllRacesSortedByClosestLocationAscending(latitude, longitude);
        var racesOptional2 = raceService.getAllRacesSortedByClosestLocationAscending(latitude2, longitude2);
        if(racesOptional.isPresent() && racesOptional2.isPresent()){
            var races = racesOptional.get();
            var races2 = racesOptional.get();
            for(int i = 0; i < races.size(); i++){
                assertEquals((races2.get(i)), races.get(i));
            }
        }
    }*/

}
