package no.hvl.dat251.gr9.lopbackend.services;

import no.hvl.dat251.gr9.lopbackend.entities.dao.AccountDAO;
import no.hvl.dat251.gr9.lopbackend.entities.dto.LoginDTO;
import no.hvl.dat251.gr9.lopbackend.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;


    public Optional<AccountDAO> save(LoginDTO account) {
        var acc = new AccountDAO();
        acc.setUsername(account.getUsername());
        return Optional.of(accountRepository.save(acc));
    }

    public Optional<List<AccountDAO>> getAllAccounts() {
        var res = accountRepository.findAll();
        return Optional.of(res);
    }

    public Optional<AccountDAO> getAccountByEmail(String email) {
        var res = accountRepository.findByUsername(email);
        return Optional.of(res);
    }
}
