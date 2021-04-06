package no.hvl.dat251.gr9.lopbackend.controllers;

import no.hvl.dat251.gr9.lopbackend.entities.UserAccount;
import no.hvl.dat251.gr9.lopbackend.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AccountsController {

    @Autowired
    private AuthService authService;

    @ApiIgnore
    @GetMapping("/accounts")
    public ResponseEntity<List<UserAccount>> getAllAccounts() {

        var allAccounts = authService.getAllAccounts();

        if(allAccounts.isEmpty()) {
            HttpStatus status = HttpStatus.NO_CONTENT;
            return new ResponseEntity("empty",status);
        }

        HttpStatus status = HttpStatus.OK;
        return new ResponseEntity<>(allAccounts.get(), status);
    }

}
