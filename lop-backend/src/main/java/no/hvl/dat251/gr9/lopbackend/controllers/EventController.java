package no.hvl.dat251.gr9.lopbackend.controllers;

import no.hvl.dat251.gr9.lopbackend.config.security.JwtTokenProvider;
import no.hvl.dat251.gr9.lopbackend.entities.dto.EventDTO;
import no.hvl.dat251.gr9.lopbackend.entities.dto.LocationDTO;
import no.hvl.dat251.gr9.lopbackend.entities.dto.RaceDTO;
import no.hvl.dat251.gr9.lopbackend.services.EventService;
import no.hvl.dat251.gr9.lopbackend.services.OrganizerService;
import no.hvl.dat251.gr9.lopbackend.services.RaceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;

@RestController
@RequestMapping("api/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @Autowired
    private RaceService raceService;

    @Autowired
    JwtTokenProvider jwtControl;

    @Autowired
    private OrganizerService organizerService;

    private final Logger logger = LoggerFactory.getLogger(AuthenticateController.class);

    @GetMapping(value = "/")
    public ResponseEntity<?> getAllEvents() {
        var res = eventService.getAllEvents();
        if(res.isPresent()) {
            return new ResponseEntity<>(res.get(), HttpStatus.OK);
        }
        logger.error("No events found");
        return new ResponseEntity<>("No events found", HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getEvent(@PathVariable("id") final String id) {
        var res = eventService.getEvent(id);
        if(res.isPresent()) {
            return new ResponseEntity<>(res.get(), HttpStatus.OK);
        }
        logger.error("Event with id:", id, "not found");
        return new ResponseEntity<>("Event with given id not found", HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/")
    public ResponseEntity<?> createEvent(@NotNull @RequestHeader("Authorization") final String token,
                                         @RequestBody EventDTO newEvent) {
        var accountid = jwtControl.parseHeader(token);
        var organizer = organizerService.getAccountById(accountid.get());
        if(accountid.isPresent() && organizer.isPresent()) {
            EventDTO event = new EventDTO(
                 newEvent.getName(),
                 newEvent.getEventStart(),
                 newEvent.getGeneralInfo(),
                 new ArrayList<>(),
                 organizer.get().getProfile().getContacts(),
                 newEvent.getLocation(),
                 organizer.get().getProfile()
            );

            var res = eventService.add(event);

            if(res.isPresent()) {
                return new ResponseEntity<>(res.get(), HttpStatus.OK);
            }
        }
        logger.error("Could not create competition", newEvent);
        return new ResponseEntity<>("Could not create new event", HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/{id}/race")
    public ResponseEntity<?> createRace(@NotNull @RequestHeader("Authorization") final String token,
                                        @RequestBody RaceDTO newRace, String eventId) {
        var accountid = jwtControl.parseHeader(token);

        if(accountid.isPresent() && organizerService.getAccountById(accountid.get()).isPresent()) {
            var res = raceService.add(newRace, eventId);

            if (res.isPresent()) {
                return new ResponseEntity<>(res.get(), HttpStatus.OK);
            }
        }
        logger.error("Could not create race in event with following id: ", eventId);
        return new ResponseEntity<>("Could not create race in event with following id: " + eventId, HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "/{id}/race")
    public ResponseEntity<?> getAllRaces() {
        var res = raceService.getAllRaces();
        if(res.isPresent()) {
            return new ResponseEntity<>(res.get(), HttpStatus.OK);
        }
        logger.error("No races found");
        return new ResponseEntity<>("No races found", HttpStatus.NOT_FOUND);
    }


}
