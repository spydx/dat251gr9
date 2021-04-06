package no.hvl.dat251.gr9.lopbackend.integration.authenticate;

import com.fasterxml.jackson.databind.ObjectMapper;
import no.hvl.dat251.gr9.lopbackend.entities.dto.UserAccountDTO;
import no.hvl.dat251.gr9.lopbackend.entities.dto.LoginDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.not;
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
        var brokenUser = new LoginDTO();
        brokenUser.setEmail("idonthave@email.com");
        brokenUser.setPassword("whatIsPassword");
        var jsonBrokenUser = OBJECT_MAPPER.writeValueAsString(brokenUser);
        mvc.perform(
                post(apiEndpoint)
                        .header("Cache-Control", "no-cache")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBrokenUser))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    void givenNonExistingUser_whenRegister_thenStatus201OK() throws Exception {
        UserAccountDTO newAccount = new UserAccountDTO(
                "test",
                "test",
                null,
                null,
                null,
                "test@test.no",
                "testtest123");

        var apiEndpoint = "/api/auth/register";
        var jsonBrokenUser = OBJECT_MAPPER.writeValueAsString(newAccount);

        mvc.perform(
                post(apiEndpoint)
                    .header("Cache-Control", "no-cache")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonBrokenUser))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void givenExistingUser_whenRegister_thenStatus400BadRequest() throws Exception {
        UserAccountDTO newAccount = new UserAccountDTO(
                "test",
                "test",
                 null,
                 null,
                 null,
                 email,
                "testtest123");

        var apiEndpoint = "/api/auth/register";
        var jsonBrokenUser = OBJECT_MAPPER.writeValueAsString(newAccount);

        mvc.perform(
                post(apiEndpoint)
                        .header("Cache-Control", "no-cache")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBrokenUser))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

}
