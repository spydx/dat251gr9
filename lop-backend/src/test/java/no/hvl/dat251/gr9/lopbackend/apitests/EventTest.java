package no.hvl.dat251.gr9.lopbackend.apitests;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import no.hvl.dat251.gr9.lopbackend.controllers.EventController;
import no.hvl.dat251.gr9.lopbackend.entities.Competition;
import no.hvl.dat251.gr9.lopbackend.entities.dao.CompetitionDAO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


@RunWith(SpringRunner.class)
@WebMvcTest(value = EventController.class)
public class EventTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    protected MockMvc mockMvc;

    @Before
    public void setup() throws Exception
    {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void getAllCompetitionsTest() throws Exception {

        this.mockMvc.perform(get("/"))
                .andExpect(status().is2xxSuccessful())
                .andDo(print());
    }
}
