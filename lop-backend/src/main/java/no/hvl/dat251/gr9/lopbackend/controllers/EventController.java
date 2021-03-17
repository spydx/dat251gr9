package no.hvl.dat251.gr9.lopbackend.controllers;

import no.hvl.dat251.gr9.lopbackend.entities.dto.EventDTO;
import no.hvl.dat251.gr9.lopbackend.entities.dto.RaceDTO;
import no.hvl.dat251.gr9.lopbackend.services.EventService;
import no.hvl.dat251.gr9.lopbackend.services.RaceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @Autowired
    private RaceService raceService;

    private final Logger logger = LoggerFactory.getLogger(AuthenticateController.class);

    @GetMapping(value = "/")
    public ResponseEntity<?> getAllCompetition() {
        var res = eventService.getAllEvents();
        if(res.isPresent()) {
            return new ResponseEntity<>(res.get(), HttpStatus.OK);
        }
        logger.error("No events found");
        return new ResponseEntity<>("No events found", HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getCompetition(@PathVariable("id") final String id) {
        var res = eventService.getEvent(id);
        if(res.isPresent()) {
            return new ResponseEntity<>(res.get(), HttpStatus.OK);
        }
        logger.error("Event with id:", id, "not found");
        return new ResponseEntity<>("Event with given id not found", HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/")
    public ResponseEntity<?> createCompetition(@RequestBody EventDTO newComp) {
        var res = eventService.add(newComp);

        if(res.isPresent()) {
            return new ResponseEntity<>(res.get(), HttpStatus.OK);
        }
        logger.error("Could not create competition", newComp);
        return new ResponseEntity<>("Could not create new competition", HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/{id}/race")
    public ResponseEntity<?> createRace(@RequestBody RaceDTO newRace, String eventId) {
        var res = raceService.add(newRace, eventId);
        if(res.isPresent()) {
            return new ResponseEntity<>(res.get(), HttpStatus.OK);
        }
        logger.error("Could not create race in event with following id: ", eventId);
        return new ResponseEntity<>("Could not create race in event with following id: " + eventId, HttpStatus.BAD_REQUEST);
    }
}
