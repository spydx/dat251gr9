package no.hvl.dat251.gr9.lopbackend.entities.dao;

import no.hvl.dat251.gr9.lopbackend.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountDAO extends JpaRepository<Account, Long> {
    public Account save(Account accounts);
    public List<Account> findAll();
    Optional<Account> findByEmail(String email);
    Optional<Account> findById(String id);
}
