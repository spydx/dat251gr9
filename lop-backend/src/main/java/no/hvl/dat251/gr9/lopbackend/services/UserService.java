package no.hvl.dat251.gr9.lopbackend.services;


import no.hvl.dat251.gr9.lopbackend.config.response.InternalServerError;
import no.hvl.dat251.gr9.lopbackend.entities.UserAccount;
import no.hvl.dat251.gr9.lopbackend.entities.UserProfile;
import no.hvl.dat251.gr9.lopbackend.entities.RoleEnum;
import no.hvl.dat251.gr9.lopbackend.entities.dao.UserAccountDAO;
import no.hvl.dat251.gr9.lopbackend.entities.dao.RoleDAO;
import no.hvl.dat251.gr9.lopbackend.entities.dao.UserProfileDAO;
import no.hvl.dat251.gr9.lopbackend.entities.dto.SignUpFormDTO;
import no.hvl.dat251.gr9.lopbackend.entities.dto.PasswordDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserAccountDAO userAccountStorage;

    @Autowired
    private UserProfileDAO profileStorage;

    @Autowired
    private RoleDAO roleStorage;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final Logger logger = LoggerFactory.getLogger(UserService.class);

    public Optional<UserAccount> getAccount(String email) {
        return userAccountStorage.findByEmail(email);
    }

    public Optional<UserAccount> add(SignUpFormDTO newaccount) {
        var account = new UserAccount(newaccount.getEmail());
        var role = roleStorage.findByRole(RoleEnum.USER).orElseThrow(
                () -> new InternalServerError("User Role not set")
        );
        account.setRoles(Collections.singleton(role));
        account.setPassword(passwordEncoder.encode(newaccount.getPassword()));
        var profile = new UserProfile();
        profile.setFirstname(newaccount.getFirstname());
        profile.setLastname(newaccount.getLastname());
        profile.setBirthdate(newaccount.getBirthdate());
        profile.setAddress(newaccount.getAddress());
        profile.setCity(newaccount.getCity());

        account.setProfile(profile);
        var acc = userAccountStorage.save(account);
        var pro = acc.getProfile();
        pro.setAccount(acc);
        profileStorage.save(pro);

        return Optional.of(acc);
    }

    public Optional<UserAccount> updateAccount(String profileid, PasswordDTO updated) {
        var profile = profileStorage.findById(profileid);
        if(profile.isPresent()) {
            var account = profile.get().getAccount();
            if(account.getEmail().equals(updated.getEmail())) {
                account.setPassword(passwordEncoder.encode(updated.getPassword()));
                return Optional.of(userAccountStorage.save(account));
            }
            logger.error("Cant find profile for {}", updated );
        }
        return Optional.empty();
    }
}
