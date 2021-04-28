package no.hvl.dat251.gr9.lopbackend.services;

import no.hvl.dat251.gr9.lopbackend.config.security.AccountPrincipals;
import no.hvl.dat251.gr9.lopbackend.entities.UserAccount;
import no.hvl.dat251.gr9.lopbackend.entities.dao.OrganizerAccountDAO;
import no.hvl.dat251.gr9.lopbackend.entities.dao.UserAccountDAO;
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
    private UserAccountDAO userAccountDAO;

    @Autowired
    private OrganizerAccountDAO organizerAccountDAO;


    public Optional<UserAccount> save(LoginDTO account) {
        var acc = new UserAccount();
        acc.setEmail(account.getEmail());
        return Optional.of(userAccountDAO.save(acc));
    }

    public Optional<List<UserAccount>> getAllAccounts() {
        var res = userAccountDAO.findAll();
        return Optional.of(res);
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        var userAccount = userAccountDAO.findByEmail(username);
        var organizerAccount = organizerAccountDAO.findByEmail(username);

        if(userAccount.isPresent()){
            return AccountPrincipals.create(userAccount.get());
        }

        if(organizerAccount.isPresent()){
            return AccountPrincipals.create(organizerAccount.get());
        }

        throw new UsernameNotFoundException("Bad credentials");
    }

    public Optional<UserDetails> loadUserById(String id) {
        var userAccount = userAccountDAO.findById(id);
        var organizerAccount = organizerAccountDAO.findById(id);

        if(userAccount.isPresent()){
            return Optional.of(AccountPrincipals.create(userAccount.get()));
        }

        if(organizerAccount.isPresent()){
            return Optional.of(AccountPrincipals.create(organizerAccount.get()));
        }

        throw new UsernameNotFoundException("User with id:"+ id+" not found");
    }

    public Optional<UserAccount> getAccountByEmail(String email) {
        return userAccountDAO.findByEmail(email);
    }
}


