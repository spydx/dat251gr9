package no.hvl.dat251.gr9.lopbackend.integration.roles;

import no.hvl.dat251.gr9.lopbackend.entities.RoleEnum;
import no.hvl.dat251.gr9.lopbackend.entities.dao.RoleDAO;
import no.hvl.dat251.gr9.lopbackend.integration.IntegrationTestContextConfiguration;
import no.hvl.dat251.gr9.lopbackend.services.SetupService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@ExtendWith(SpringExtension.class)
@DataJpaTest
@Import(IntegrationTestContextConfiguration.class)
@ActiveProfiles("test")
public class RolesDAOIntegrationTest {

    @Autowired
    private RoleDAO roleDAO;


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
