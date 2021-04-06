package no.hvl.dat251.gr9.lopbackend.entities.dao;

import no.hvl.dat251.gr9.lopbackend.entities.OrganizerAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrganizerAccountDAO extends JpaRepository<OrganizerAccount, String> {
    public OrganizerAccount save(OrganizerAccount account);
    public List<OrganizerAccount> findAll();
    Optional<OrganizerAccount> findByEmail(String email);
    Optional<OrganizerAccount> findById(String id);
}
