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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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
        List<Race> bcmRaces = new ArrayList<>();
        GregorianCalendar bcmDate = new GregorianCalendar(2021,Calendar.APRIL,  24);
        var bcmEvent = new EventDTO("Bergen City Marathon", bcmDate, "info", bcmRaces);
        logger.info("Created event \"Bergen City Marathon\"");
        var createBcmEvent = eventService.add(bcmEvent);
        if(createBcmEvent.isPresent()){
            logger.info("Created new event " + createBcmEvent.get());

            GregorianCalendar bcmMarathonStart = new GregorianCalendar(2021, Calendar.APRIL, 24, 8, 0);
            var bcmMarathonRace = new RaceDTO(42.195f, bcmMarathonStart, 500f, false,
                    false,false, false, false, false, "info");
            var createBcmMarathonRace= raceService.add(bcmMarathonRace, createBcmEvent.get().getId());

            if(createBcmMarathonRace.isPresent()){
                logger.info("Created marathon race for BCM: " + createBcmMarathonRace.get());
            }else{
                logger.error("Failed to create marathon race for BCM");
            }

            GregorianCalendar bcmHalfMarathonStart = new GregorianCalendar(2021, Calendar.APRIL, 24, 10, 0);
            var bcmHalfMarathonRace = new RaceDTO(21.0975f, bcmHalfMarathonStart, 250f, false,
                    false,false, false, false, false, "info");
            var createBcmHalfMarathonRace= raceService.add(bcmHalfMarathonRace, createBcmEvent.get().getId());

            if(createBcmHalfMarathonRace.isPresent()){
                logger.info("Created half marathon race for BCM: " + createBcmHalfMarathonRace.get());
            }else{
                logger.error("Failed to create half marathon race for BCM");
            }

        }else {
            logger.error("Failed to create BCM event");
        }
    }
}






