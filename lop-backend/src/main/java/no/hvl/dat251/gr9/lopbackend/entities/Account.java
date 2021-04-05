package no.hvl.dat251.gr9.lopbackend.entities;

import java.util.Set;

public interface Account {

    String getId();

    String getEmail();

    String getPassword();

    Set<Roles> getRoles();
}
