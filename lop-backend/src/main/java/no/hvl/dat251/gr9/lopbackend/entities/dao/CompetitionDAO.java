package no.hvl.dat251.gr9.lopbackend.entities.dao;

import no.hvl.dat251.gr9.lopbackend.entities.Competition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompetitionDAO extends JpaRepository<Competition, String> {
    public Competition save(Competition competition);
    Optional<Competition> findById(String id);
    public List<Competition> findAll();

}
