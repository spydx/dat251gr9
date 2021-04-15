package no.hvl.dat251.gr9.lopbackend.entities.dao;

import no.hvl.dat251.gr9.lopbackend.entities.Race;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RaceDAO extends JpaRepository<Race, String> {
    Race save(Race race);
    Optional<Race> findById(String id);
    List<Race> findAll();
    List<Race> findByOrderByParticipantsAsc();
    List<Race> findByOrderByDistanceAsc();
    List<Race> findByOrderByParticipantsDesc();
    List<Race> findByOrderByDistanceDesc();
}

