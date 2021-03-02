package no.hvl.dat251.gr9.lopbackend.services;


import no.hvl.dat251.gr9.lopbackend.config.response.InternalServerError;
import no.hvl.dat251.gr9.lopbackend.entities.Account;
import no.hvl.dat251.gr9.lopbackend.entities.Profile;
import no.hvl.dat251.gr9.lopbackend.entities.RoleEnum;
import no.hvl.dat251.gr9.lopbackend.entities.dao.AccountDAO;
import no.hvl.dat251.gr9.lopbackend.entities.dao.ProfileDAO;
import no.hvl.dat251.gr9.lopbackend.entities.dao.RoleDAO;
import no.hvl.dat251.gr9.lopbackend.entities.dto.AccountDTO;
import no.hvl.dat251.gr9.lopbackend.entities.dto.PasswordDTO;
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
    private ProfileDAO profileStorage;

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
        var profile = new Profile();
        profile.setFirstname(newaccount.getFirstname());
        profile.setLastname(newaccount.getLastname());

        account.setProfile(profile);
        var acc = accountStorage.save(account);
        var pro = acc.getProfile();
        pro.setAccount(acc);
        profileStorage.save(pro);

        return Optional.of(acc);
    }

    public Optional<Account> updateAccount(String profileid, PasswordDTO updated) {
        var profile = profileStorage.find(profileid);
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
