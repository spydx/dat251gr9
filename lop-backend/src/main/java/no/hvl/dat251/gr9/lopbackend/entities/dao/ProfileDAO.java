package no.hvl.dat251.gr9.lopbackend.entities.dao;


import no.hvl.dat251.gr9.lopbackend.entities.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
//@Transactional
public interface ProfileDAO extends JpaRepository<Profile, String> {
    public Profile save(Profile profile);
    public Optional<Profile> findById(String id);
    public List<Profile> findAll();
}
