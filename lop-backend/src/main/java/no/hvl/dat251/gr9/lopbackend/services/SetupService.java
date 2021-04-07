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
    private OrganizerService organizerService;

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
            var newAdmin = new UserAccountDTO(
                    "Lop",
                    "Admin",
                    null,
                    null,
                    null,
                    email,
                    password
            );

            var create = userService.add(newAdmin);
            if(create.isPresent())
                logger.info("Created admin account {}", create.get());
            else
                logger.error("Failed to create admin account");

        } else {
            logger.info("Account with email: {} already exist", exist.get().getEmail());

        }

        var organiserExist = organizerService.getAccount("organizer@test.no");
        if(organiserExist.isEmpty()){

            var testContact = new Contacts("Contact #1", "contact1@test.no", "12345678");
            var testContact2 = new Contacts("Contact #2", "contact2@test.no", "34235645");
            var testContact3 = new Contacts("Contact #3", "contact3@test.no", "34212364");

            List<Contacts> contacts = new ArrayList<>();
            contacts.add(testContact);
            contacts.add(testContact2);
            contacts.add(testContact3);

            var newOrganizer = new OrganizerAccountDTO(
                    "Organizer",
                    "Some place on earth",
                    contacts,
                    "organizer@test.no",
                    "test123"
            );
            var createOrganizer = organizerService.add(newOrganizer);

            if(createOrganizer.isPresent()) {
                logger.info("Created organizer account {}", createOrganizer.get());
            }
            else
                logger.info("Failed to create organizer account");
        } else {
            logger.info("Organizer account with email: organizer@test.no already exist");
        }


        var organizer = organizerService.getAccount("organizer@test.no");
        if(!organizer.isPresent()){
            logger.info("init failed!");
            return;
        }

        //----------------------------------------------------------------------------------
        var testEvent = new EventDTO("Test Marathon", LocalDate.of(2021, 4, 24), "info", new ArrayList<>(), organizer.get().getProfile().getContact(), null, organizer.get().getProfile());

        var testMarathonRace = new RaceDTO(42.195f, LocalTime.of(15, 30), 500f, false,
                false,false, false, false, false, "info");
        var testHalfMarathonRace = new RaceDTO(21.0975f, LocalTime.of(16, 30), 250f, false,
                false,false, false, false, false, "info");



        var testLocation = new LocationDTO("Vestland", "Bergen", "Bergen",60.396803, 5.323383);

        List<RaceDTO> races = new ArrayList<>();
        races.add(testMarathonRace);
        races.add(testHalfMarathonRace);


        createEvent(testEvent, races, testLocation);
        //----------------------------------------------------------------------------------
        var testEvent2 = new EventDTO("Test event number 2", LocalDate.of(2021, 8, 2), "this is a test event", new ArrayList<>(), organizer.get().getProfile().getContact(), null, organizer.get().getProfile());

        var testRace21 = new RaceDTO(1.0f, LocalTime.of(1, 50), 2.0f, false,
                true, false, true, false, true, "This race is a test race for event "+ testEvent2.getName());
        var testRace22 = new RaceDTO(100.0f, LocalTime.of(2, 50), 2.0f, false,
                true, false, true, false, true, "This race is also a test race for event "+ testEvent2.getName());


        var testLocation2 = new LocationDTO("Test county #2", "Test municipality #2", "Test place #2", 12.34, 56.789);
        races.clear();
        races.add(testRace21);
        races.add(testRace22);


        createEvent(testEvent2, races, testLocation2);
        //----------------------------------------------------------------------------------

    }

    public void createEvent(EventDTO event, List<RaceDTO> races, LocationDTO location){
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


    public void createLocation(LocationDTO newLocation, String eventId){
        var createLocation = locationService.add(newLocation, eventId);
        if(createLocation.isPresent()){
            logger.info("Created location: " + createLocation.get() + " for event " + eventId);
        } else {
            logger.info("Failed to create location: " + createLocation + " for event " + eventId);
        }
    }
}






