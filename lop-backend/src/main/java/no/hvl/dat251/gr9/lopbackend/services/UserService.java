package no.hvl.dat251.gr9.lopbackend.services;


import no.hvl.dat251.gr9.lopbackend.config.response.InternalServerError;
import no.hvl.dat251.gr9.lopbackend.entities.Account;
import no.hvl.dat251.gr9.lopbackend.entities.RoleEnum;
import no.hvl.dat251.gr9.lopbackend.entities.dao.AccountDAO;
import no.hvl.dat251.gr9.lopbackend.entities.dao.RoleDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class UserService {

    @Value("anon@lop.no")
    private String anonymousUser;

    @Autowired
    private AccountDAO accountStorage;

    @Autowired
    private RoleDAO roleStorage;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final Logger logger = LoggerFactory.getLogger(UserService.class);

    public Optional<Account> getAccount(String email) {
        return accountStorage.findByEmail(email);
    }

    public Optional<Account> add(AccountDTO newaccount) {
        var account = new Account(newaccount.getEmail());
        var role = roleStorage.findByRole(RoleEnum.USER).orElseThrow(
                () -> new InternalServerError("User Role not set")
        );
        account.setRoles(Collections.singleton(role));
        account.setPassword(passwordEncoder.encode(newaccount.getPassword()));
        var profile = new Profile(newaccount.getFirstname(), newaccount.getLastname());

        account.setProfile(profile);
        var acc = accountStorage.save(account);
        var pro = acc.get().getProfile();
        pro.setAccount(acc.get());
        profileStorage.save(pro);
        return acc;

    }

    public Optional<Account> updateAccount(String profileid, PasswordDTO updated) {
        var profile = profileStorage.get(profileid);
        if(profile.isPresent()) {
            var account = profile.get().getAccount();
            if(account.getEmail().equals(updated.getEmail())) {
                account.setPassword(passwordEncoder.encode(updated.getPassword()));
                return accountStorage.update(account);
            }
            logger.error("Cant find profile for {}", updated );
        }
        return Optional.empty();
    }
}
