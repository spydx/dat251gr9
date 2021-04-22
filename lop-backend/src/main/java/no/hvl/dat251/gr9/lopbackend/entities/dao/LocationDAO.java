package no.hvl.dat251.gr9.lopbackend.entities.dao;

import no.hvl.dat251.gr9.lopbackend.entities.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LocationDAO extends JpaRepository<Location, String> {
    public Location save(Location location);
    public void deleteById(String id);
    public List<Location> findAll();
    Optional<Location> findById(String id);
    Optional<Location> findByLatitudeAndLongitude(double latitude, double longitude);
    Optional<Location> findByCountyAndMunicipalityAndPlace(String county, String municipality, String place);
}
