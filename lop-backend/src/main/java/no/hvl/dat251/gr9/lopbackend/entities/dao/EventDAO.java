package no.hvl.dat251.gr9.lopbackend.entities.dao;

import no.hvl.dat251.gr9.lopbackend.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventDAO extends JpaRepository<Event, String> {
    public Event save(Event competition);
    Optional<Event> findById(String id);
    Optional<Event> findEventByName(String name);
    public List<Event> findAll();
    List<Event> findByNameContains(String term);
    List<Event> findByOrderByEventStartDesc();
    List<Event> findByOrderByEventStartAsc();
}
