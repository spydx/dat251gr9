package no.hvl.dat251.gr9.lopbackend.services;

import no.hvl.dat251.gr9.lopbackend.config.security.AccountPrincipals;
import no.hvl.dat251.gr9.lopbackend.entities.UserAccount;
import no.hvl.dat251.gr9.lopbackend.entities.dao.AccountDAO;
import no.hvl.dat251.gr9.lopbackend.entities.dto.LoginDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    private AccountDAO accountDAO;


    public Optional<UserAccount> save(LoginDTO account) {
        var acc = new UserAccount();
        acc.setEmail(account.getEmail());
        return Optional.of(accountDAO.save(acc));
    }

    public Optional<List<UserAccount>> getAllAccounts() {
        var res = accountDAO.findAll();
        return Optional.of(res);
    }

    public UserDetails loadUserByUsername(String username) {
        var account = accountDAO.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));

        return AccountPrincipals.create(account);
    }

    public Optional<UserDetails> loadUserById(String id) {
        var account = accountDAO.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));

        return Optional.ofNullable(AccountPrincipals.create(account));

    }

    public Optional<UserAccount> getAccountByEmail(String email) {
        return accountDAO.findByEmail(email);
    }
}


