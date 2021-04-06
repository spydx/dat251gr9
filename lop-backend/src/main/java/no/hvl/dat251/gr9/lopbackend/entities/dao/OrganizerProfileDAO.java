package no.hvl.dat251.gr9.lopbackend.entities.dao;

import no.hvl.dat251.gr9.lopbackend.entities.OrganizerProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrganizerProfileDAO extends JpaRepository<OrganizerProfile, String> {
    public OrganizerProfile save(OrganizerProfile profile);
    public Optional<OrganizerProfile> findById(String id);
    public List<OrganizerProfile> findAll();
}

