package no.hvl.dat251.gr9.lopbackend.services;

import no.hvl.dat251.gr9.lopbackend.entities.Competition;
import no.hvl.dat251.gr9.lopbackend.entities.dao.CompetitionDAO;
import no.hvl.dat251.gr9.lopbackend.entities.dto.CompetitionDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    @Autowired
    private CompetitionDAO competitionStorage;

    private final Logger logger = LoggerFactory.getLogger(EventService.class);

    public Optional<List<Competition>> getAllCompetitions() {
        return Optional.of(competitionStorage.findAll());
    }

    public Optional<Competition> getCompetition(String id) { return competitionStorage.findById(id); }

    public Optional<Competition> add(CompetitionDTO newComp) {
        var competition = new Competition(newComp.getName(), newComp.getEventStart(), newComp.getGeneralInfo());
        var comp = competitionStorage.save(competition);

        return Optional.of(comp);

    }

    public Optional<Competition> updateCompetition(String id, CompetitionDTO updated) {
        var competition = competitionStorage.findById(id);
        if(competition.isPresent()) {
            competition.get().setName(updated.getName());
            competition.get().setEventStart(updated.getEventStart());
            competition.get().setGeneralInfo(updated.getGeneralInfo());

            return Optional.of(competitionStorage.save(competition.get()));
        }
        logger.error("Cant find competition for {}", updated);

        return Optional.empty();
    }

}
