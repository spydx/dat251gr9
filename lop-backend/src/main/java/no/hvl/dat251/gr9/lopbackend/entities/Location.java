package no.hvl.dat251.gr9.lopbackend.entities;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class Location {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid2")
    private String id;

    private String county; //fylke
    private String municipality; //kommune
    private String place; //by/bygd/tettsted
    private double latitude; //north-south
    private double longitude; //east-west

    public Location(String county, String municipality, String place, double latitude, double longitude){
        this.county = county;
        this.municipality = municipality;
        this.place = place;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Location(){

    }
}
