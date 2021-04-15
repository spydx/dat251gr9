package no.hvl.dat251.gr9.lopbackend.integration.race;

import no.hvl.dat251.gr9.lopbackend.entities.Race;
import no.hvl.dat251.gr9.lopbackend.entities.dao.RaceDAO;
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

import java.time.LocalTime;

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


}
