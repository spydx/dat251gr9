package no.hvl.dat251.gr9.lopbackend.integration.authenticate;

import no.hvl.dat251.gr9.lopbackend.entities.UserAccount;
import no.hvl.dat251.gr9.lopbackend.entities.dao.UserAccountDAO;
import no.hvl.dat251.gr9.lopbackend.services.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
class AuthServiceIntegrationTest {

    @TestConfiguration
    static class AccountServiceTestContext {
        @Bean
        public AuthService accountService() {return new AuthService();}
    }

    @Autowired
    private AuthService authService;

    @MockBean
    private UserAccountDAO userAccountDAO;

    @BeforeEach
    void setUp() {
        var email = "fossen.kenneth@gmail.com";
        var acc = new UserAccount();
        acc.setEmail(email);
        acc.setPassword("passord");

        Mockito.when(userAccountDAO.findByEmail(acc.getEmail()))
                .thenReturn(Optional.of(acc));
    }

    @Test
    void whenValidEmail_ThenAccountShouldBeFound() {
        var email = "fossen.kenneth@gmail.com";
        var found = authService.getAccountByEmail(email);
        assertTrue(found.isPresent());
        assertThat(found.get().getEmail())
                .isEqualTo(email);
    }
}
