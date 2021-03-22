package no.hvl.dat251.gr9.lopbackend.integration.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import no.hvl.dat251.gr9.lopbackend.config.security.JwtAuthenticationResponse;
import no.hvl.dat251.gr9.lopbackend.entities.dto.EventDTO;
import no.hvl.dat251.gr9.lopbackend.entities.dto.LoginDTO;
import no.hvl.dat251.gr9.lopbackend.entities.dto.RaceDTO;
import no.hvl.dat251.gr9.lopbackend.services.EventService;
import no.hvl.dat251.gr9.lopbackend.services.RaceService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
import java.util.GregorianCalendar;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@EnableAutoConfiguration
@AutoConfigureTestDatabase
@ActiveProfiles("test")
class EventRestControllerIntegrationTest {
    String AUTHORIZATION = "Authorization";
    String BEARER = "Bearer ";

    @Value("${app.email}")
    private String email;

    @Value("${app.password}")
    private String password;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private EventService eventService;

    @Autowired
    private RaceService raceService;

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Test
    void guestUser_whenGetEvents_thenStatus200() throws Exception {
        createEventAndRace();
        var apiEndpoint = "/api/events/";
        mvc.perform(
                get(apiEndpoint).contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))));
    }

    @Test
    void givenAccount_whenGetEvent_thenStatus200() throws Exception {
        createEventAndRace();
        var apiEndpoint = "/api/events/";
        var cred = performLogin();

        mvc.perform(
                get(apiEndpoint)
                        .contentType(MediaType.APPLICATION_JSON)
        .header(AUTHORIZATION, BEARER + cred.getToken()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))))
                .andExpect(jsonPath("$[0].name", is("Testløp")));

    }

    @Test
    void givenAccount_whenGetEvent_EventContainsRace() throws Exception {
        createEventAndRace();
        var apiEndpoint = "/api/events/";
        var cred = performLogin();

        mvc.perform(
                get(apiEndpoint)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(AUTHORIZATION, BEARER + cred.getToken()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))))
                .andExpect(jsonPath("$[0].name", is("Testløp")))
                .andExpect((jsonPath("$[0].races", hasSize(greaterThan(0)))));

    }

    private JwtAuthenticationResponse performLogin() throws Exception  {
        var apiLoginEndpoint = "/api/auth/login";
        var cred = new LoginDTO();
        cred.setEmail(email);
        cred.setPassword(password);
        ObjectMapper objectMapper = new ObjectMapper();
        var jsoncred = objectMapper.writeValueAsString(cred);
        var res = mvc.perform(
                post(apiLoginEndpoint).contentType(MediaType.APPLICATION_JSON)
                        .content(jsoncred))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();
        return objectMapper.readValue(res.getResponse().getContentAsString(), JwtAuthenticationResponse.class);
    }

    private void createEventAndRace() {
        var newcomp = new EventDTO("Testløp", new GregorianCalendar(), "ammagaaad", null);

        var newRace = new RaceDTO(
                1.0f,
                new GregorianCalendar(),
                1.0f,
                true,
                true,
                true,
                true,
                true,
                true,
                ":)"
        );

        var event = eventService.add(newcomp);
        raceService.add(newRace, event.get().getId());
    }
}
