package no.hvl.dat251.gr9.lopbackend.services;

import no.hvl.dat251.gr9.lopbackend.entities.Event;
import no.hvl.dat251.gr9.lopbackend.entities.Race;
import no.hvl.dat251.gr9.lopbackend.entities.RoleEnum;
import no.hvl.dat251.gr9.lopbackend.entities.Roles;
import no.hvl.dat251.gr9.lopbackend.entities.dao.AccountDAO;
import no.hvl.dat251.gr9.lopbackend.entities.dao.EventDAO;
import no.hvl.dat251.gr9.lopbackend.entities.dao.RaceDAO;
import no.hvl.dat251.gr9.lopbackend.entities.dao.RoleDAO;
import no.hvl.dat251.gr9.lopbackend.entities.dto.AccountDTO;
import no.hvl.dat251.gr9.lopbackend.entities.dto.EventDTO;
import no.hvl.dat251.gr9.lopbackend.entities.dto.RaceDTO;
import org.apache.tomcat.jni.Local;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
    private AccountDAO accountStorage;

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

    private final Logger logger = LoggerFactory.getLogger(SetupService.class);

    public void init() {
        if(roleStorage.findAll().size() == 0) {
            var user = roleStorage.save(new Roles(RoleEnum.USER));
            var admin = roleStorage.save(new Roles(RoleEnum.ADMIN));
            logger.info("Created ROLES: {}, {}", user, admin);
        }

        var exist = userService.getAccount(email);
        if(exist.isEmpty()) {
            var newadmin = new AccountDTO(
                    "Lop",
                    "Admin",
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


        var testEvent = new EventDTO("Test Marathon", LocalDate.now(), "info", new ArrayList<>());

        var exists = eventStorage.findEventByName("Test Marathon");
        if(exists.isPresent()){ // skipp if event exists
            logger.info("Event " + testEvent.getName() + " already exists. Skipping initialisation!");
            return;
        }

        var createTestEvent = eventService.add(testEvent);
        if(createTestEvent.isPresent()){
            logger.info("Created new event " + createTestEvent.get());

            LocalDate testMarathonStart = LocalDate.of(2021, 4, 24);
            var testMarathonRace = new RaceDTO(42.195f, testMarathonStart, 500f, false,
                    false,false, false, false, false, "info");
            var creatTestMarathonRace= raceService.add(testMarathonRace, createTestEvent.get().getId());

            if(creatTestMarathonRace.isPresent()){
                logger.info("Created marathon race for BCM: " + creatTestMarathonRace.get());
            }else{
                logger.error("Failed to create marathon race for BCM");
            }

            LocalDate testHalfMarathonStart = LocalDate.of(2021, 4, 24);
            var testHalfMarathonRace = new RaceDTO(21.0975f, testHalfMarathonStart, 250f, false,
                    false,false, false, false, false, "info");
            var createTestHalfMarathonRace= raceService.add(testHalfMarathonRace, createTestEvent.get().getId());

            if(createTestHalfMarathonRace.isPresent()){
                logger.info("Created half marathon race for BCM: " + createTestHalfMarathonRace.get());
            }else{
                logger.error("Failed to create half marathon race for Test event");
            }

        }else {
            logger.error("Failed to create BCM event");
        }
    }
}






