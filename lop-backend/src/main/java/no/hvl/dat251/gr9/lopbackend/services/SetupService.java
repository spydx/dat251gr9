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

        List<Contacts> contacts = new ArrayList<>();
        contacts.add(testContact);
        contacts.add(testContact2);
        contacts.add(testContact3);

        var organiserExist = organizerService.getAccount("organizer@test.no");
        if(organiserExist.isEmpty()){

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
                organiserExist = createOrganizer;
            } else {
                logger.info("Failed to create organizer account");
            }
        } else {
            logger.info("Organizer account with email: organizer@test.no already exist");
        }


        //----------------------------------------------------------------------------------
        /*
        There seems to be something wrong with starttime/LocalTime for races
        Adding contacts to events is causing errors (i've ignored contacts for now as a solution)
        Updating event/race information will not currently update the information in the database.
        This is because they are not created if they already exists. To fix this you can delete all the events/races
        in the db and they will be updated the next time you run the application.
        We should implement a better way to do this, e.g. checking if the information has been updated and then
        updating the db.
         */


        //Currently a solution to update the database when you change the information for some test event/race/location
        // is to delete all events/races/locations from it. This should be a problem once the database has data that is not test data

        eventStorage.deleteAll();
        raceStorage.deleteAll();
        locationStorage.deleteAll();

        var testMarathonRace = new RaceDTO(42.195f, LocalTime.of(8, 0), 500f, false,
                false,false, false, false, false, "info");
        var testHalfMarathonRace = new RaceDTO(21.0975f, LocalTime.of(10, 0), 250f, false,
                false,false, false, false, false, "info");
        testMarathonRace.setParticipants(100);
        testHalfMarathonRace.setParticipants(50);
        List<RaceDTO> races = new ArrayList<>();
        races.add(testMarathonRace);
        races.add(testHalfMarathonRace);

        var location = new LocationDTO("Vestland", "Bergen", "Bergen",60.396803, 5.323383);
        var testEvent = new EventDTO("Test Marathon", LocalDate.of(2021, 4, 24),
                "info", new ArrayList<>(), new ArrayList<>(), null, organiserExist.get().getProfile());



        createEvent(testEvent, races, location, contacts);
        //----------------------------------------------------------------------------------
        var testRace21 = new RaceDTO(1.0f, LocalTime.of(21, 0), 2.0f, false,
                true, false, true, false, true, "This race is a test race for test event 2");
        var testRace22 = new RaceDTO(100.0f, LocalTime.of(12, 0), 2.0f, false,
                true, false, true, false, true, "This race is also a test race for test event 2 ");
        testRace21.setParticipants(1);
        testRace22.setParticipants(2);
        races.clear();
        races.add(testRace21);
        races.add(testRace22);

        var location2 = new LocationDTO("Test county #2", "Test municipality #2", "Test place #2", 12.34, 56.789);
        var testEvent2 = new EventDTO("Test event number 2", LocalDate.of(2021, 8, 2),
                "this is a test event", new ArrayList<>(), new ArrayList<>(), null, organiserExist.get().getProfile());


        createEvent(testEvent2, races, location2, contacts);
        //----------------------------------------------------------------------------------

    }

    public void createEvent(EventDTO event, List<RaceDTO> races, LocationDTO location, List<Contacts> contacts){
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

            /*
            for(Contacts contact : contacts){
                createContact(contact, newEvent.get().getId());
            }

             */

            createLocation(location, newEvent.get().getId());

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

    public void createContact(Contacts contact, String eventId){
        ContactsDTO newContact = new ContactsDTO(contact.getName(), contact.getEmail(), contact.getPhone());
        var createContact = contactsService.add(newContact, eventId);
        if(createContact.isPresent()){
            logger.info("Created contact: " + createContact.get() + " for event " + eventId);
        } else {
            logger.info("Failed to create contact: " + createContact + " for event " + eventId);
        }
    }


}






