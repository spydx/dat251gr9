package no.hvl.dat251.gr9.lopbackend.integration.account;


import no.hvl.dat251.gr9.lopbackend.entities.dao.AccountDAO;
import no.hvl.dat251.gr9.lopbackend.repositories.AccountRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.util.Arrays;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class AccountRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AccountRepository accountRepository;

    private int numberOfAccounts = 0;

    @BeforeEach
    private void setUp() {
        var emails = new String[]{
                "email1@gmail.no",
                "emial2@hotmail.com",
                "email3@uib.no",
                "eamil4@hvl.no"
        };

        for (var e : emails) {
            var acc = new AccountDAO();
            acc.setUsername(e);
            var pwd = e.split("@")[0];
            acc.setPassword(pwd);
            entityManager.persist(acc);
        }
        numberOfAccounts = emails.length;
    }

    @Test
    void persistAccount() {
        var acc = new AccountDAO();
        var username = "test@gmail.com";
        var password = "password";
        acc.setUsername(username);
        acc.setPassword(password);

        entityManager.persist(acc);

        var found = accountRepository.findByUsername(username);
        assertThat(found.getUsername())
                .isEqualTo(acc.getUsername());
    }

    @Test
    void findAllAccounts() {
        var res = accountRepository.findAll();
        assertThat(res.size())
                .isEqualTo(numberOfAccounts);
        assertThat(res.size())
                .isNotZero();
    }


}
