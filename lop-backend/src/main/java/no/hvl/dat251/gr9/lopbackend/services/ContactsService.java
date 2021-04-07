package no.hvl.dat251.gr9.lopbackend.services;

import no.hvl.dat251.gr9.lopbackend.entities.Contacts;
import no.hvl.dat251.gr9.lopbackend.entities.Event;
import no.hvl.dat251.gr9.lopbackend.entities.dao.ContactsDAO;
import no.hvl.dat251.gr9.lopbackend.entities.dao.EventDAO;
import no.hvl.dat251.gr9.lopbackend.entities.dto.ContactsDTO;
import no.hvl.dat251.gr9.lopbackend.entities.dto.EventDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ContactsService {

    @Autowired
    private ContactsDAO contactsStorage;

    @Autowired
    private EventDAO eventStorage;

    @Autowired
    private EventService eventService;

    private final Logger logger = LoggerFactory.getLogger(ContactsService.class);


    public Optional<List<Contacts>> getAllContacts() { return Optional.of(contactsStorage.findAll()); }

    public Optional<Contacts> getContact(String id) { return contactsStorage.findById(id); }

    @Transactional
    public Optional<Contacts> add(ContactsDTO newContact, String eventId) {
        var event = eventStorage.findById(eventId);
        if(event.isPresent()){
            var contact = new Contacts(
                    newContact.getName(),
                    newContact.getEmail(),
                    newContact.getPhone()
            );

            var c = contactsStorage.save(contact);
            Event ev = event.get();
            EventDTO updatedEvent = new EventDTO(
                    ev.getName(),
                    ev.getEventStart(),
                    ev.getGeneralInfo(),
                    ev.getRaces(),
                    ev.getContacts(),
                    ev.getLocation(),
                    ev.getOrganizer()
            );
            updatedEvent.getContacts().add(c);
            eventService.updateEvent(eventId, updatedEvent);
            return Optional.of(c);
        }
        logger.error("Could not add new contact to event: {}", eventId);
        return Optional.empty();
    }
}
