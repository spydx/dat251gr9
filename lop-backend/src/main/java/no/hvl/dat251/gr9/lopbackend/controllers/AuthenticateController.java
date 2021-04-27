package no.hvl.dat251.gr9.lopbackend.controllers;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import no.hvl.dat251.gr9.lopbackend.config.response.TokenValidationResponse;
import no.hvl.dat251.gr9.lopbackend.config.security.JwtAuthenticationResponse;
import no.hvl.dat251.gr9.lopbackend.config.security.JwtTokenProvider;
import no.hvl.dat251.gr9.lopbackend.entities.UserAccount;
import no.hvl.dat251.gr9.lopbackend.entities.dto.CreateUserAccountDTO;
import no.hvl.dat251.gr9.lopbackend.entities.dto.LoginDTO;
import no.hvl.dat251.gr9.lopbackend.services.OrganizerService;
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
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.*;

@RestController
@RequestMapping("api/auth")
public class AuthenticateController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UserService userService;

    @Autowired
    private OrganizerService organizerService;

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

        var userexists = userService.getAccount(login.getEmail());
        var organizerexists = organizerService.getAccount(login.getEmail());

        if (userexists.isPresent() && organizerexists.isPresent()) {
            logger.info("This should not be possible. Please contact IT-support.");

        } else if (userexists.isPresent()) {

            var profileid = userexists.get().getProfile().getId();
            return ResponseEntity.ok(new JwtAuthenticationResponse(token, profileid));

        } else if (organizerexists.isPresent()) {

            var orgprofileid = organizerexists.get().getProfile().getId();
            return ResponseEntity.ok(new JwtAuthenticationResponse(token, orgprofileid));
        }
        logger.error("Log-in error failed for {}", login.getEmail());
        return ResponseEntity.ok("failed to find user profile");
    }

    @PostMapping(value = "/register")
    public ResponseEntity<?> createUser(
            @NotNull @Valid @RequestBody CreateUserAccountDTO newUser,
            BindingResult bindingResult
    ) {
        @Data
        @RequiredArgsConstructor
        class FormValidationError {
            final String error = "FORM_VALIDATION_ERROR";
            final Map<String, List<String>> fieldsErrors;
            final Collection<String> globalErrors;
        }

        @Data
        @RequiredArgsConstructor
        class UnknownError {
            final String error = "UNKNOWN_ERROR";
            final String message;
        }

        if (bindingResult.hasErrors()) {
            var fieldErrors = new HashMap<String, List<String>>();
            for (var e : bindingResult.getFieldErrors()) {
                fieldErrors.computeIfAbsent(e.getField(), s -> new ArrayList<>()).add(e.getDefaultMessage());
            }

            var globalErrors = new ArrayList<String>();
            for (ObjectError e : bindingResult.getGlobalErrors()) {
                globalErrors.add(e.getDefaultMessage());
            }

            FormValidationError formError = new FormValidationError(fieldErrors, globalErrors);
            return ResponseEntity.badRequest().body(formError);
        }

        boolean alreadyRegistered = userService.getAccount(newUser.getEmail()).isPresent();
        if (alreadyRegistered) {
            FormValidationError formError = new FormValidationError(Map.of(), List.of("Email Address already in use"));
            return ResponseEntity.badRequest().body(formError);
        }

        var maybeNewAccount = userService.add(newUser);
        if (maybeNewAccount.isEmpty()) {
            return ResponseEntity.badRequest().body(new UnknownError("Could not create account")); // TODO: why does this happen?
        }

        UserAccount newAccount = maybeNewAccount.get();
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/users/{email}")
                .buildAndExpand(newAccount.getProfile().getId())
                .toUri();
        return ResponseEntity.created(location).body("Registered");
    }

    @PostMapping(value = "/validateToken")
    public ResponseEntity<?> validateToken(final String token) {
        if (tokenProvider.validateToken(token)) {
            return new ResponseEntity<>(new TokenValidationResponse(token, true, tokenProvider.tokenExpirationDate(token)), HttpStatus.OK);
        }
        return new ResponseEntity<>(new TokenValidationResponse(token, false, tokenProvider.tokenExpirationDate(token)), HttpStatus.OK);
    }
}
