package no.hvl.dat251.gr9.lopbackend.integration.authenticate;

import com.fasterxml.jackson.databind.ObjectMapper;
import no.hvl.dat251.gr9.lopbackend.config.security.JwtAuthenticationResponse;
import no.hvl.dat251.gr9.lopbackend.entities.dto.LoginDTO;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthenticateHelper {
    private static String AUTHORIZATION = "Authorization";
    private static String BEARER = "Bearer ";

    public static JwtAuthenticationResponse performLogin(MockMvc mvc, String email, String password) throws Exception  {
        var apiLoginEndpoint = "/api/auth/login";
        var cred = new LoginDTO();
        cred.setEmail(email);
        cred.setPassword(password);
        ObjectMapper objectMapper = new ObjectMapper();
        var jsoncred = objectMapper.writeValueAsString(cred);
        var res = mvc.perform(
                post(apiLoginEndpoint).contentType(MediaType.APPLICATION_JSON)
                        .content(jsoncred)
                        .header("Cache-Control", "no-cache"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();
        return objectMapper.readValue(res.getResponse().getContentAsString(), JwtAuthenticationResponse.class);
    }
}
