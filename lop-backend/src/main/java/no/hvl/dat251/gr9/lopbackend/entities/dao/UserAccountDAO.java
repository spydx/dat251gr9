package no.hvl.dat251.gr9.lopbackend.entities.dao;

import no.hvl.dat251.gr9.lopbackend.entities.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserAccountDAO extends JpaRepository<UserAccount, String> {
    public UserAccount save(UserAccount account);
    public List<UserAccount> findAll();
    Optional<UserAccount> findByEmail(String email);
    Optional<UserAccount> findById(String id);
}
