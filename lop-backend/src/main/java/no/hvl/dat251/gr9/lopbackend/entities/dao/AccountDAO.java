package no.hvl.dat251.gr9.lopbackend.repositories;

import no.hvl.dat251.gr9.lopbackend.entities.dao.AccountDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<AccountDAO, Long> {
    public AccountDAO findByUsername(String username);
    public AccountDAO save(AccountDAO accounts);
    public List<AccountDAO> findAll();
}
