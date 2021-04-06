package no.hvl.dat251.gr9.lopbackend.services;

import no.hvl.dat251.gr9.lopbackend.entities.*;
import no.hvl.dat251.gr9.lopbackend.entities.dao.UserAccountDAO;
import no.hvl.dat251.gr9.lopbackend.entities.dao.EventDAO;
import no.hvl.dat251.gr9.lopbackend.entities.dao.RoleDAO;
import no.hvl.dat251.gr9.lopbackend.entities.dto.*;
import org.apache.tomcat.jni.Local;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Service
public class SetupService {

    @Value("${app.email}")
    private String email;

    @Value("${app.password}")
    private String password;

    @Autowired
    private UserService userService;

    @Autowired
    private UserAccountDAO accountStorage;

    @Autowired
    private RoleDAO roleStorage;

    @Autowired
    private EventService eventService;

    @Autowired
    private EventDAO eventStorage;

    @Autowired
    private RaceService raceService;

    @Autowired
    private ContactsService contactsService;

    @Autowired
    private LocationService locationService;

    private final Logger logger = LoggerFactory.getLogger(SetupService.class);

    public void init() {
        if(roleStorage.findAll().size() == 0) {
            var user = roleStorage.save(new Roles(RoleEnum.USER));
            var admin = roleStorage.save(new Roles(RoleEnum.ADMIN));
            logger.info("Created ROLES: {}, {}", user, admin);
        }

        var exist = userService.getAccount(email);
        if(exist.isEmpty()) {
            var newadmin = new UserAccountDTO(
                    "Lop",
                    "Admin",
                    null,
                    null,
                    null,
                    email,
                    password
            );

            var create = userService.add(newadmin);
            if(create.isPresent())
                logger.info("Created admin account {}", create.get());
            else
                logger.error("Failed to create admin account");

        } else {
            logger.info("Account with email: {} already exist", exist.get().getEmail());

        }

        //----------------------------------------------------------------------------------
        var testEvent = new EventDTO("Test Marathon", LocalDate.of(2021, 4, 24), "info", new ArrayList<>(), new ArrayList<>(), new Location());

        var testMarathonRace = new RaceDTO(42.195f, LocalTime.of(15, 30), 500f, false,
                false,false, false, false, false, "info");
        var testHalfMarathonRace = new RaceDTO(21.0975f, LocalTime.of(16, 30), 250f, false,
                false,false, false, false, false, "info");

        var testContact = new ContactsDTO("Contact #1", "contact1@test.no", "12345678");
        var testContact2 = new ContactsDTO("Contact #2", "contact2@test.no", "34235645");

        var testLocation = new LocationDTO("Vestland", "Bergen", "Bergen",60.396803, 5.323383);

        List<RaceDTO> races = new ArrayList<>();
        List<ContactsDTO> contacts = new ArrayList<>();
        races.add(testMarathonRace);
        races.add(testHalfMarathonRace);
        contacts.add(testContact);
        contacts.add(testContact2);

        createEvent(testEvent, races, contacts, testLocation);
        //----------------------------------------------------------------------------------
        var testEvent2 = new EventDTO("Test event number 2", LocalDate.of(2021, 8, 2), "this is a test event", new ArrayList<>(), new ArrayList<>(), new Location());

        var testRace21 = new RaceDTO(1.0f, LocalTime.of(1, 50), 2.0f, false,
                true, false, true, false, true, "This race is a test race for event "+ testEvent2.getName());
        var testRace22 = new RaceDTO(100.0f, LocalTime.of(2, 50), 2.0f, false,
                true, false, true, false, true, "This race is also a test race for event "+ testEvent2.getName());

        var testContact3 = new ContactsDTO("Contact #3", "contact3@test.no", "34212364");

        var testLocation2 = new LocationDTO("Test county #2", "Test municipality #2", "Test place #2", 12.34, 56.789);
        races.clear();
        races.add(testRace21);
        races.add(testRace22);
        contacts.add(testContact3);

        createEvent(testEvent2, races, contacts, testLocation2);
        //----------------------------------------------------------------------------------

    }

    public void createEvent(EventDTO event, List<RaceDTO> races, List<ContactsDTO> contacts, LocationDTO location){
        var exists = eventStorage.findEventByName(event.getName());

        if(exists.isPresent()){
            logger.info("Event already exists: " + event.getName());
            return;
        }

        var newEvent = eventService.add(event);
        if(newEvent.isPresent()){
            logger.info("Created new event " + newEvent.get().getName());

            for(RaceDTO race: races){
                createRace(race, newEvent.get().getId());
            }
            for(ContactsDTO contact: contacts){
                createContact(contact, newEvent.get().getId());
            }

            createLocation(location, newEvent.get().getId());
        }else{
            logger.info("Failed to create event ");
        }
    }

    public void createRace(RaceDTO newRace, String eventId){
        var createRace = raceService.add(newRace, eventId);
        if(createRace.isPresent()){
            logger.info("Created race: " + newRace + " for event " + eventId);
        } else {
            logger.info("Failed to create race: " + newRace + " for event " + eventId);
        }
    }

    public void createContact(ContactsDTO newContact, String eventId){
        var createContact = contactsService.add(newContact, eventId);
        if(createContact.isPresent()){
            logger.info("Created contact: " + createContact.get() + " for event " + eventId);
        } else {
            logger.info("Failed to create contact: " + createContact + " for event " + eventId);
        }
    }

    public void createLocation(LocationDTO newLocation, String eventId){
        var createLocation = locationService.add(newLocation, eventId);
        if(createLocation.isPresent()){
            logger.info("Created location: " + createLocation.get() + " for event " + eventId);
        } else {
            logger.info("Failed to create location: " + createLocation + " for event " + eventId);
        }
    }
}






