package no.hvl.dat251.gr9.lopbackend.integration.event;

import no.hvl.dat251.gr9.lopbackend.entities.Event;
import no.hvl.dat251.gr9.lopbackend.entities.Location;
import no.hvl.dat251.gr9.lopbackend.geocoding.APIRequest;
import no.hvl.dat251.gr9.lopbackend.integration.IntegrationTestContextConfiguration;
import no.hvl.dat251.gr9.lopbackend.services.EventService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Locale;
import java.util.concurrent.ExecutionException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;



@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test")
@Import(IntegrationTestContextConfiguration.class)
public class EventSearchTest {

    @Autowired
    private EventService eventService;

    @Test
    void sortByClosestLocationAscending(){
        double latitude = 0;
        double longitude = 0;
        var res = eventService.getAllEvents();
        if(res.isPresent()){
            res  = eventService.getEventsSortedByClosestLocationAscending(res.get(), latitude, longitude);
            if(res.isPresent()){
                var events = res.get();
                for(int i = 1; i < events.size(); i++){
                    Location loc1 = events.get(i-1).getLocation();
                    Location loc2 = events.get(i).getLocation();
                    double distLoc1 = loc1.getDistanceBetweenLocations(latitude, longitude);
                    double distLoc2 = loc2.getDistanceBetweenLocations(latitude, longitude);
                    assertTrue(distLoc1 <= distLoc2);
                }
            }

        }
    }

    @Test
    void searchByLocationAsStringAndSortByClosestAsc() throws ExecutionException, InterruptedException {
        double[] latitudeAndLongitude = APIRequest.getLatitudeAndLongitude("Bergen Vestland");
        double latitude = latitudeAndLongitude[0];
        double longitude = latitudeAndLongitude[1];
        double latitude2 = 60.39299;
        double longitude2 = 5.32415;
        var res = eventService.getAllEvents();
        if(res.isPresent()){
            var res1 = eventService.getEventsSortedByClosestLocationAscending(res.get(), latitude, longitude);
            var res2 = eventService.getEventsSortedByClosestLocationAscending(res.get(), latitude2, longitude2);

            if(res1.isPresent() && res2.isPresent()){
                var events = res1.get();
                var events2 = res2.get();
                for(int i = 0; i < events.size(); i++){
                    assertEquals((events.get(i)), events2.get(i));
                }
            }

        }
    }

    @Test
    void searchByTerm(){
        String searchTerm = "test";
        var res = eventService.getEventsByTerm(searchTerm);
        if(res.isPresent()){
            var events = res.get();
            for(Event event : events){
                assertTrue(event.getName().toLowerCase(Locale.ROOT).contains(searchTerm));
            }
        }
    }

    @Test
    void sortEventsByEventStartDesc(){
        var res = eventService.getAllEvents();
        if(res.isPresent()){
            res =  eventService.sortBy(res.get(), "EVENT_START_DESC");
            if(res.isPresent()){
                var events = res.get();
                for(int i = 1; i < events.size(); i++){
                    int compare = events.get(i-1).getEventStart().compareTo(events.get(i).getEventStart());
                    assertTrue(compare >= 0);
                }
            }

        }
    }

    @Test
    void sortEventsByEventStartAsc(){
        var res = eventService.getAllEvents();
        if(res.isPresent()){
            res =  eventService.sortBy(res.get(), "EVENT_START_ASC");
            if(res.isPresent()){
                var events = res.get();
                for(int i = 1; i < events.size(); i++) {
                    int compare = events.get(i - 1).getEventStart().compareTo(events.get(i).getEventStart());
                    assertTrue(compare <= 0);
                }
            }
        }
    }

}
