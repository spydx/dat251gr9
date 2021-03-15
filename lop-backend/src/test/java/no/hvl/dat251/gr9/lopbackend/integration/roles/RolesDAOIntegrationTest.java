package no.hvl.dat251.gr9.lopbackend.integration.roles;

import no.hvl.dat251.gr9.lopbackend.entities.RoleEnum;
import no.hvl.dat251.gr9.lopbackend.entities.Roles;
import no.hvl.dat251.gr9.lopbackend.entities.dao.RoleDAO;
import no.hvl.dat251.gr9.lopbackend.integration.IntegrationTestContextConfiguration;
import no.hvl.dat251.gr9.lopbackend.services.SetupService;
import org.aspectj.lang.annotation.Before;
import org.h2.engine.Role;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Import(IntegrationTestContextConfiguration.class)
@ActiveProfiles("test")
public class RolesDAOIntegrationTest {

    @Autowired
    private SetupService setupService;

    @Autowired
    private RoleDAO roleDAO;

    @BeforeEach
    public void setup() {
        setupService.init();

    }

    @Test
    public void findUserRole() {
        var userRole = roleDAO.findByRole(RoleEnum.USER);
        assertThat(userRole.get().getRole())
                .isEqualTo(RoleEnum.USER);
    }

    @Test
    public void findAdminRole() {
        var adminRole =roleDAO.findByRole(RoleEnum.ADMIN);
        assertThat(adminRole.get().getRole())
                .isEqualTo(RoleEnum.ADMIN);
    }
}
