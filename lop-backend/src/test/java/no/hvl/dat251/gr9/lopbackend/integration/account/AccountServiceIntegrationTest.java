package no.hvl.dat251.gr9.lopbackend.integration.account;

import no.hvl.dat251.gr9.lopbackend.entities.dao.AccountDAO;
import no.hvl.dat251.gr9.lopbackend.repositories.AccountRepository;
import no.hvl.dat251.gr9.lopbackend.services.AccountService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
class AccountServiceIntegrationTest {

    @TestConfiguration
    static class AccountServiceTestContext {
        @Bean
        public AccountService accountService() {return new AccountService();}
    }

    @Autowired
    private AccountService accountService;

    @MockBean
    private AccountRepository accountRepository;

    @BeforeEach
    void setUp() {
        var email = "fossen.kenneth@gmail.com";
        var acc = new AccountDAO();
        acc.setUsername(email);
        acc.setPassword("passord");

        Mockito.when(accountRepository.findByUsername(acc.getUsername()))
                .thenReturn(acc);
    }

    @Test
    void whenValidEmail_ThenAccountShouldBeFound() {
        var email = "fossen.kenneth@gmail.com";
        var found = accountService.getAccountByEmail(email);
        assertTrue(found.isPresent());
        assertThat(found.get().getUsername())
                .isEqualTo(email);
    }
}
