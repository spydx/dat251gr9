package no.hvl.dat251.gr9.lopbackend.entities;

import no.hvl.dat251.gr9.lopbackend.entities.dto.RaceDTO;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RaceTest {

    @Test
    void raceGetsetTest(){

        Float distance = 1.0f;
        Date starttime = new Date();
        Float elevation = 1.0f;
        Boolean hillRun = true;
        Boolean children = true;
        Boolean womenOnly = true;
        Boolean relay = true;
        Boolean multisport = true;
        Boolean obstacleRun = true;
        String info = "test :)";

        var race = new Race();

        race.setDistance(distance);
        race.setStarttime(starttime);
        race.setElevation(elevation);
        race.setHillRun(hillRun);
        race.setChildren(children);
        race.setWomenOnly(womenOnly);
        race.setRelay(relay);
        race.setMultiSport(multisport);
        race.setObstacleRun(obstacleRun);
        race.setInfo(info);


        assertEquals(distance, race.getDistance());
        assertEquals(starttime, race.getStarttime());
        assertEquals(elevation, race.getElevation());
        assertEquals(hillRun, race.getHillRun());
        assertEquals(children, race.getChildren());
        assertEquals(womenOnly, race.getWomenOnly());
        assertEquals(relay, race.getRelay());
        assertEquals(multisport, race.getMultiSport());
        assertEquals(obstacleRun, race.getObstacleRun());
        assertEquals(info, race.getInfo());

    }
}
