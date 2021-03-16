package no.hvl.dat251.gr9.lopbackend.integration.authenticate;

import com.fasterxml.jackson.databind.ObjectMapper;
import no.hvl.dat251.gr9.lopbackend.config.security.JwtAuthenticationResponse;
import no.hvl.dat251.gr9.lopbackend.entities.dto.CompetitionDTO;
import no.hvl.dat251.gr9.lopbackend.entities.dto.LoginDTO;
import no.hvl.dat251.gr9.lopbackend.services.CompetitionService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@EnableAutoConfiguration(exclude = SecurityAutoConfiguration.class)
@AutoConfigureTestDatabase
@ActiveProfiles("test")
class AuthenticateRestControllerIntegrationTest {
    String AUTHORIZATION = "Authorization";
    String BEARER = "Bearer";

    @Value("${app.email}")
    private String email;

    @Value("${app.password}")
    private String password;

    @Autowired
    private MockMvc mvc;

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Test
    void givenCredentials_whenLogin_thenStatus200_andToken() throws Exception  {

        var apiLoginEndpoint = "/api/auth/login";
        var cred = new LoginDTO();
        cred.setEmail(email);
        cred.setPassword(password);
        ObjectMapper objectMapper = new ObjectMapper();
        var jsoncred = objectMapper.writeValueAsString(cred);
        mvc.perform(
                post(apiLoginEndpoint).contentType(MediaType.APPLICATION_JSON)
                        .content(jsoncred)
                        .header("Cache-Control", "no-cache"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.token", not("")))
                .andExpect(jsonPath("$.tokenType", is(BEARER)))
                .andExpect(jsonPath("$.profile", not("")));
    }

    @Test
    void givenNonExistingUser_whenLogin_thenStatus401() throws Exception {
        var apiEndpoint = "/api/auth/login";
        var brokenuser = new LoginDTO();
        brokenuser.setEmail("idonthave@email.com");
        brokenuser.setPassword("whatIsPassword");
        var jsonbrokenuser = OBJECT_MAPPER.writeValueAsString(brokenuser);
        mvc.perform(
                post(apiEndpoint)
                        .header("Cache-Control", "no-cache")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonbrokenuser))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    //TODO: Test for Register Account

}
