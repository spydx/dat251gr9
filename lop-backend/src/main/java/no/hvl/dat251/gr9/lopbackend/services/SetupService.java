package no.hvl.dat251.gr9.lopbackend.services;

import no.hvl.dat251.gr9.lopbackend.entities.*;
import no.hvl.dat251.gr9.lopbackend.entities.dao.*;
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
    private RaceDAO raceStorage;

    @Autowired
    private ContactsService contactsService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private LocationDAO locationStorage;

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

        var testContact = new Contacts("Contact #1", "contact1@test.no", "12345678");
        var testContact2 = new Contacts("Contact #2", "contact2@test.no", "34235645");
        var testContact3 = new Contacts("Contact #3", "contact3@test.no", "34212364");

        List<Contacts> eventContacts = new ArrayList<>();
        eventContacts.add(testContact);
        eventContacts.add(testContact2);
        eventContacts.add(testContact3);

        var organiserExist = organizerService.getAccount("organizer@test.no");
        if(organiserExist.isEmpty()){

            var newOrganizer = new OrganizerAccountDTO(
                    "Organizer",
                    "Some place on earth",
                    eventContacts,
                    "organizer@test.no",
                    "test123"
            );
            var createOrganizer = organizerService.add(newOrganizer);

            if(createOrganizer.isPresent()) {
                logger.info("Created organizer account {}", createOrganizer.get());
                organiserExist = createOrganizer;
            } else {
                logger.info("Failed to create organizer account");
            }
        } else {
            logger.info("Organizer account with email: organizer@test.no already exist");
        }

        OrganizerProfile organizerProfile = organiserExist.get().getProfile();

        //----------------------------------------------------------------------------------
              /*
        There seems to be something wrong with starttime/LocalTime for races
        Adding contacts to events is causing errors (i've ignored contacts for now as a solution)
        Updating event/race information will not currently update the information in the database.
        This is because events are not created if they already exist. To fix this you can delete all the events/races
        in the db and they will be updated the next time you run the application.
        We should implement a better way to do this, e.g. checking if the information has been updated and then
        updating the db.
         */


        //Currently a solution to update the database when you change the information for some test event/race/location
        // is to delete all events/races/locations from it. This should be a problem once the database has data that is not test data

        eventStorage.deleteAll();
        raceStorage.deleteAll();
        locationStorage.deleteAll();
        {
            var testEvent = new EventDTO("Test Marathon", LocalDate.of(2021, 4, 24), "info", new ArrayList<>(), null, null, organizerProfile);
            var testMarathonRace = new RaceDTO(42.195f, LocalTime.of(15, 0), 500f, false,
                    false, false, false, false, false, "info");
            var testHalfMarathonRace = new RaceDTO(21.0975f, LocalTime.of(16, 0), 250f, false,
                    false, false, false, false, false, "info");
            var testLocation = new LocationDTO("Vestland", "Bergen", "Bergen", 60.396803, 5.323383);
            createEvent(testEvent, List.of(testMarathonRace, testHalfMarathonRace), testLocation);
        }
        {
            var event = new EventDTO("Test event number 2", LocalDate.of(2021, 8, 2), "this is a test event", new ArrayList<>(), null, null, organizerProfile);
            var race1 = new RaceDTO(1.0f, LocalTime.of(1, 0), 2.0f, false,
                    true, false, true, false, true, "This race is a test race for event " + event.getName());
            var race2 = new RaceDTO(100.0f, LocalTime.of(2, 0), 2.0f, false,
                    true, false, true, false, true, "This race is also a test race for event " + event.getName());
            var testLocation2 = new LocationDTO("Test county #2", "Test municipality #2", "Test place #2", 12.34, 56.789);
            createEvent(event, List.of(race1, race2), testLocation2);
        }
        {
            var location = new LocationDTO("Oslo", "Oslo", "Oslo", 59.911491, 10.757933);
            var event = new EventDTO("Oslo 5k (Test)", LocalDate.of(2021, 5, 20), "Info om arrangementet", List.of(), null, null, organizerProfile);
            var race = new RaceDTO(5f, LocalTime.of(12, 0), 0f, true, false, false, false, false, false, "5K for voksne");
            createEvent(event, List.of(race), location);
        }
        {
            var location = new LocationDTO("Trøndelag", "Trondheim", "Trondheim", 63.446827, 10.421906);
            var event = new EventDTO("Trondheim 10k (Test)", LocalDate.of(2021, 5, 20), "10 km passer for deg som syns maratondistansene er for lang, mens 5 km blir litt for kort. Med litt treningsbakgrunn vil dette kunne bli en fantastisk løpsopplevelse i Trondheim by. Hvor raskt klarer du å løpe mila?", List.of(), null, null, organizerProfile);
            var race1 = new RaceDTO(10f, LocalTime.of(11, 0), 50f, true, false, false, false, false, false, "10K for menn");
            var race2 = new RaceDTO(10f, LocalTime.of(13, 0), 50f, true, false, true, false, false, false, "10K for kvinner");
            createEvent(event, List.of(race1, race2), location);
        }
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

            createLocation(location, newEvent.get().getId());

            for(RaceDTO race: races){
                createRace(race, newEvent.get().getId());
            }

        }else{
            logger.info("Failed to create event ");
        }
    }

    public void createRace(RaceDTO newRace, String eventId){
        var createRace = raceService.add(newRace, eventId);
        if(createRace.isPresent()){
            logger.info("Created race: " + createRace.get() + " for event " + eventId);
        } else {
            logger.info("Failed to create race: " + createRace + " for event " + eventId);
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






