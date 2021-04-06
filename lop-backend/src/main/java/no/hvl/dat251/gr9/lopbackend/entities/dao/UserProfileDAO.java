package no.hvl.dat251.gr9.lopbackend.entities.dao;


import no.hvl.dat251.gr9.lopbackend.entities.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserProfileDAO extends JpaRepository<UserProfile, String> {
    public UserProfile save(UserProfile profile);
    public Optional<UserProfile> findById(String id);
    public List<UserProfile> findAll();
}
