package no.hvl.dat251.gr9.lopbackend.controllers;

import io.swagger.annotations.Example;
import io.swagger.annotations.ExampleProperty;
import no.hvl.dat251.gr9.lopbackend.config.security.JwtTokenProvider;
import no.hvl.dat251.gr9.lopbackend.entities.Event;
import no.hvl.dat251.gr9.lopbackend.entities.Location;
import no.hvl.dat251.gr9.lopbackend.entities.dto.EventDTO;
import no.hvl.dat251.gr9.lopbackend.entities.dto.RaceDTO;
import no.hvl.dat251.gr9.lopbackend.geocoding.APIRequest;
import no.hvl.dat251.gr9.lopbackend.services.EventService;
import no.hvl.dat251.gr9.lopbackend.services.OrganizerService;
import no.hvl.dat251.gr9.lopbackend.services.RaceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.core.annotation.AliasFor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

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
    public ResponseEntity<?> getEvent(@PathVariable("id") String id) {
        var res = eventService.getEvent(id);
        if(res.isPresent()) {
            return new ResponseEntity<>(res.get(), HttpStatus.OK);
        }
        logger.error("Event with id:", id, "not found");
        return new ResponseEntity<>("Event with given id not found", HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/")
    public ResponseEntity<?> createEvent(@NotNull @RequestHeader("Authorization") String token,
                                         @RequestBody EventDTO newEvent) {
        var accountId = jwtControl.parseHeader(token);
        if (accountId.isPresent()){
            var organizer = organizerService.getAccountById(accountId.get());
            if(organizer.isPresent()) {
                EventDTO event = new EventDTO(
                        newEvent.getName(),
                        newEvent.getEventStart(),
                        newEvent.getGeneralInfo(),
                        new ArrayList<>(),
                        new ArrayList<>(organizer.get().getProfile().getContacts()),
                        newEvent.getLocation(),
                        organizer.get().getProfile()
                );

                var res = eventService.add(event);

                if(res.isPresent()) {
                    return new ResponseEntity<>(res.get(), HttpStatus.OK);
                }
            }
        }
        logger.error("Could not create new event. No organizer account.");
        return new ResponseEntity<>("Could not create new event. No organizer account.", HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/{id}/race")
    public ResponseEntity<?> createRace(@NotNull @RequestHeader("Authorization") String token,
                                        @RequestBody RaceDTO newRace, @PathVariable("id") String eventId) {
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
    public ResponseEntity<?> getAllRaces(@PathVariable("id") String eventId) {
        var res = raceService.getAllRaces();
        if(res.isPresent()) {
            return new ResponseEntity<>(res.get(), HttpStatus.OK);
        }
        logger.error("No races found");
        return new ResponseEntity<>("No races found", HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/search")
    public ResponseEntity<?> getEventsBySearch(
            @RequestParam Optional<String> term,
            @RequestParam Optional<String> geoLocName,
            @RequestParam Optional<String> sort
    ) throws ExecutionException, InterruptedException {
        var res = eventService.getAllEvents();
        if(res.isPresent()){
            if(term.isPresent()){
                res = eventService.getEventsByTerm(term.get());
            }
            if(sort.isPresent() && res.isPresent()){
                res = eventService.sortBy(res.get(), sort.get());
            }
            if(geoLocName.isPresent()  && res.isPresent()){
                double[] latitudeAndLongitude = APIRequest.getLatitudeAndLongitude(geoLocName.get());
                res = eventService.getEventsSortedByClosestLocationAscending(res.get(), latitudeAndLongitude[0], latitudeAndLongitude[1]);
            }
            if(res.isPresent()){
                return new ResponseEntity<>(res, HttpStatus.OK);
            }
            logger.error("No events found");
            return new ResponseEntity<>("No events found", HttpStatus.NOT_FOUND);
        }
        logger.error("No events found");
        return new ResponseEntity<>("No events found", HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/races/search")
    public ResponseEntity<?> getRacesBySearch(
            @RequestParam Optional<String> sort,
            @RequestParam(required = false) boolean hillRun,
            @RequestParam(required = false) boolean children,
            @RequestParam(required = false) boolean womenOnly,
            @RequestParam(required = false) boolean relay,
            @RequestParam(required = false) boolean multiSport,
            @RequestParam(required = false) boolean obstacleRun
    ){
        var res = raceService.getAllRaces();
        if(res.isPresent()){
            if(hillRun){
                res = raceService.getHillRunRaces(res.get());
            }
            if(children && res.isPresent()){
                res = raceService.getChildrenRaces(res.get());
            }
            if(womenOnly && res.isPresent()){
                res = raceService.getWomenOnlyRaces(res.get());
            }
            if(relay && res.isPresent()){
                res = raceService.getRelayRaces(res.get());
            }
            if(multiSport&& res.isPresent()){
                res = raceService.getMultiSportRaces(res.get());
            }
            if(obstacleRun && res.isPresent()){
                res = raceService.getObstacleRunRaces(res.get());
            }
            if(sort.isPresent() && res.isPresent()){
                res = raceService.sortBy(res.get(), sort.get());
            }
            if(res.isPresent()){
                return new ResponseEntity<>(res.get(), HttpStatus.OK);
            }
            logger.error("No races found");
            return new ResponseEntity<>("No races found", HttpStatus.NOT_FOUND);
        }
        logger.error("No races found");
        return new ResponseEntity<>("No races found", HttpStatus.NOT_FOUND);
    }




}
