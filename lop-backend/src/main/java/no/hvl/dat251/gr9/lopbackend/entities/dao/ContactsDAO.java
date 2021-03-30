package no.hvl.dat251.gr9.lopbackend.entities.dao;

import no.hvl.dat251.gr9.lopbackend.entities.Contacts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContactsDAO extends JpaRepository<Contacts, String> {
    public Contacts save(Contacts contacts);
    Optional<Contacts> findById(String Id);
    public List<Contacts> findAll();
}
