package no.hvl.dat251.gr9.lopbackend.services;


import no.hvl.dat251.gr9.lopbackend.config.response.InternalServerError;
import no.hvl.dat251.gr9.lopbackend.entities.*;
import no.hvl.dat251.gr9.lopbackend.entities.dao.*;
import no.hvl.dat251.gr9.lopbackend.entities.dto.OrganizerAccountDTO;
import no.hvl.dat251.gr9.lopbackend.entities.dto.PasswordDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class OrganizerService {

    @Autowired
    private OrganizerAccountDAO organizerAccountStorage;

    @Autowired
    private OrganizerProfileDAO organizerProfileStorage;

    @Autowired
    private RoleDAO roleStorage;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final Logger logger = LoggerFactory.getLogger(OrganizerService.class);

    public Optional<OrganizerAccount> getAccount(String email) {return organizerAccountStorage.findByEmail(email);}

    public Optional<OrganizerAccount> add(OrganizerAccountDTO newAccount){
        var account = new OrganizerAccount(newAccount.getEmail());
        var role = roleStorage.findByRole(RoleEnum.USER).orElseThrow(
                () -> new InternalServerError("User Role not set")
        );
        account.setRoles(Collections.singleton(role));
        account.setPassword(passwordEncoder.encode((newAccount.getPassword())));
        var profile = new OrganizerProfile();
        profile.setOrganizerName(newAccount.getOrganizerName());
        profile.setAddress(newAccount.getAddress());
        profile.setContact(newAccount.getContacts());

        account.setProfile(profile);
        var acc = organizerAccountStorage.save(account);
        var pro = acc.getProfile();
        pro.setAccount(acc);
        organizerProfileStorage.save(pro);

        return Optional.of(acc);
    }

    public Optional<OrganizerAccount> updatePassword(String profileId, PasswordDTO updated){
        var profile = organizerProfileStorage.findById(profileId);
        if(profile.isPresent()){
            var account = profile.get().getAccount();
            if(account.getEmail().equals(updated.getEmail())){
                account.setPassword(passwordEncoder.encode(updated.getPassword()));
                return Optional.of(organizerAccountStorage.save(account));
            }
            logger.info("Cant find profile for {}", updated);
        }
        return Optional.empty();
    }

    public Optional<OrganizerProfile> addContact(String profileId, Contacts contact){
        var profile = organizerProfileStorage.findById(profileId);
        if(profile.isPresent()){
            var newContacts = profile.get().getContact();
            newContacts.add(contact);
            profile.get().setContact(newContacts);
            return Optional.of(organizerProfileStorage.save(profile.get()));

        }
        logger.error("Cant find profile for id {}", profileId);
        return Optional.empty();
    }
}
