package no.hvl.dat251.gr9.lopbackend.integration.race;


import no.hvl.dat251.gr9.lopbackend.entities.Race;
import no.hvl.dat251.gr9.lopbackend.entities.dao.RaceDAO;
import no.hvl.dat251.gr9.lopbackend.integration.IntegrationTestContextConfiguration;
import no.hvl.dat251.gr9.lopbackend.services.RaceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test")
@Import(IntegrationTestContextConfiguration.class)
public class RaceSearchTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private RaceDAO raceStorage;


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
    void sortByElevationAscending(){
        var races = raceStorage.findByOrderByElevationAsc();
        for(int i = 1; i < races.size(); i++){
            assertTrue(races.get(i-1).getElevation() <= races.get(i).getElevation());
        }
    }

    @Test
    void sortByElevationDescending(){
        var races = raceStorage.findByOrderByElevationDesc();
        for(int i = 1; i < races.size(); i++){
            assertTrue(races.get(i-1).getElevation() >= races.get(i).getElevation());
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

    @Test
    void findHillRunRaces(){
        var races = raceStorage.findByHillRunIsTrue();
        for(Race race : races){
            assertTrue(race.getHillRun());
        }
    }

    @Test
    void findChildrenRaces(){
        var races = raceStorage.findByChildrenIsTrue();
        for(Race race : races){
            assertTrue(race.getChildren());
        }
    }

    @Test
    void findWomenOnlyRaces(){
        var races = raceStorage.findByWomenOnlyIsTrue();
        for(Race race : races){
            assertTrue(race.getWomenOnly());
        }
    }

    @Test
    void findRelayRaces(){
        var races = raceStorage.findByRelayIsTrue();
        for(Race race : races){
            assertTrue(race.getRelay());
        }
    }

    @Test
    void findMultiSportRaces(){
        var races = raceStorage.findByMultiSportIsTrue();
        for(Race race : races){
            assertTrue(race.getMultiSport());
        }
    }

    @Test
    void findObstacleRunRaces(){
        var races = raceStorage.findByObstacleRunIsTrue();
        for(Race race : races){
            assertTrue(race.getObstacleRun());
        }
    }


}
