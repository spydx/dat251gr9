package no.hvl.dat251.gr9.lopbackend.controllers;

import no.hvl.dat251.gr9.lopbackend.config.response.lopAPIResponse;
import no.hvl.dat251.gr9.lopbackend.config.security.JwtTokenProvider;
import no.hvl.dat251.gr9.lopbackend.config.security.JwtAuthenticationResponse;
import no.hvl.dat251.gr9.lopbackend.entities.dto.AccountDTO;
import no.hvl.dat251.gr9.lopbackend.entities.dto.LoginDTO;
import no.hvl.dat251.gr9.lopbackend.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;

@RestController
@RequestMapping("api/auth")
public class AuthenticateController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UserService userService;

    private final Logger logger = LoggerFactory.getLogger(AuthenticateController.class);

    @PostMapping(value = "/login")
    public ResponseEntity<?> authenticate(@NotNull @Valid @RequestBody LoginDTO login) {

        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        login.getEmail(),
                        login.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(auth);
        String token = tokenProvider.generateToken(auth);
        var exists = userService.getAccount(login.getEmail());
        if(exists.isPresent()) {
            var profileid = exists.get().getProfile().getId();
            return ResponseEntity.ok(new JwtAuthenticationResponse(token, profileid));
        }
        logger.error("Log-in error failed for {}", login.getEmail());
        return ResponseEntity.ok("failed to find user profile");
    }

    @PostMapping(value = "/register")
    public ResponseEntity<?> createUser(@NotNull @Valid @RequestBody AccountDTO newUser) {
        var exist = userService.getAccount(newUser.getEmail());

        if(exist.isPresent()) {
            return new ResponseEntity(new lopAPIResponse(false, "Email Address already in use!"),
                    HttpStatus.BAD_REQUEST);
        }
        var res = userService.add(newUser);

        if(res.isPresent()) {

            URI location = ServletUriComponentsBuilder
                    .fromCurrentContextPath().path("/api/users/{email}")
                    .buildAndExpand(res.get().getProfile().getId()).toUri();
            return ResponseEntity.created(location).body(new lopAPIResponse(true, "Registred"));
        }
        return new ResponseEntity<>(newUser, HttpStatus.NO_CONTENT);
    }
}
