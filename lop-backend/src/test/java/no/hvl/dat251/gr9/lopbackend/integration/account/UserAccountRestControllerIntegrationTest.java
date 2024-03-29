package no.hvl.dat251.gr9.lopbackend.integration.account;

import com.fasterxml.jackson.databind.ObjectMapper;
import no.hvl.dat251.gr9.lopbackend.config.security.JwtAuthenticationResponse;
import no.hvl.dat251.gr9.lopbackend.entities.UserAccount;
import no.hvl.dat251.gr9.lopbackend.entities.dao.UserAccountDAO;
import no.hvl.dat251.gr9.lopbackend.integration.authenticate.AuthenticateHelper;
import org.junit.jupiter.api.AfterEach;
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

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@EnableAutoConfiguration
@AutoConfigureTestDatabase
@ActiveProfiles("test")
class UserAccountRestControllerIntegrationTest {
    String AUTHORIZATION = "Authorization";
    String BEARER = "Bearer ";
    String apiEndpoint = "/api/accounts/";
    @Value("${app.email}")
    private String email;

    @Value("${app.password}")
    private String password;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserAccountDAO userAccountDAO;

    private JwtAuthenticationResponse cred;


    @AfterEach
    public void clearEntityManager() { userAccountDAO.deleteAll();}


    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Test
    void givenAccount_whenGetAccount_thenStatus200() throws Exception {

        createTestAccount("fossen.kenneth@gmail.com","password");
        createTestAccount("nix007@uib.no", "123456");
        cred = AuthenticateHelper.performLogin(mvc, email, password);
        mvc.perform(
                get(apiEndpoint)
                        .contentType(MediaType.APPLICATION_JSON)
                .header(AUTHORIZATION, BEARER + cred.getToken()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(2))))
                .andExpect(jsonPath("$[0].email", is(email)))
                .andExpect(jsonPath("$[1].email",is("fossen.kenneth@gmail.com")))
                .andExpect(jsonPath("$[2].email", is("nix007@uib.no"))
        );
    }

    @Test
    void givenAnonymous_whenGetAccount_thenStatusUnauthorized401() throws Exception {
        mvc.perform(
                get(apiEndpoint)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    private void createTestAccount(String email, String password) {
        var acc = new UserAccount();
        acc.setPassword(password);
        acc.setEmail(email);
        userAccountDAO.save(acc);
    }
}
