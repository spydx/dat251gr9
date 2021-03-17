package no.hvl.dat251.gr9.lopbackend.controllers;

import io.swagger.models.Response;
import no.hvl.dat251.gr9.lopbackend.entities.dto.CompetitionDTO;
import no.hvl.dat251.gr9.lopbackend.services.CompetitionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("api/events")
public class EventController {

    @Autowired
    private CompetitionService competitionService;

    private final Logger logger = LoggerFactory.getLogger(AuthenticateController.class);

    @GetMapping(value = "/")
    public ResponseEntity<?> getAllCompetition() {
        var res = competitionService.getAllCompetitions();
        if(res.isPresent()) {
            return new ResponseEntity<>(res.get(), HttpStatus.OK);
        }
        logger.error("No events found");
        return new ResponseEntity<>("No events found", HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getCompetition(@PathVariable("id") final String id) {
        var res = competitionService.getCompetition(id);
        if(res.isPresent()) {
            return new ResponseEntity<>(res.get(), HttpStatus.OK);
        }
        logger.error("Event with id:", id, "not found");
        return new ResponseEntity<>("Event with given id not found", HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/")
    public ResponseEntity<?> createCompetition(@RequestBody CompetitionDTO newComp) {
        var res = competitionService.add(newComp);

        if(res.isPresent()) {
            return new ResponseEntity<>(res.get(), HttpStatus.OK);
        }
        logger.error("Could not create competition", newComp);
        return new ResponseEntity<>("Could not create new competition", HttpStatus.NOT_FOUND);
    }
}
