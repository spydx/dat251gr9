package no.hvl.dat251.gr9.lopbackend.controllers;

import no.hvl.dat251.gr9.lopbackend.entities.dao.AccountDAO;
import no.hvl.dat251.gr9.lopbackend.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AccountsController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/accounts")
    public ResponseEntity<List<AccountDAO>> getAllAccounts() {

        var allAccounts = accountService.getAllAccounts();

        if(allAccounts.isEmpty()) {
            HttpStatus status = HttpStatus.NO_CONTENT;
            return new ResponseEntity("empty",status);
        }

        HttpStatus status = HttpStatus.OK;
        return new ResponseEntity<>(allAccounts.get(), status);
    }

}
