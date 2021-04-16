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

    public double getDistanceBetweenLocations(double latitude, double longitude){
        double latDiff = latitude - this.getLatitude();
        double longDiff = longitude - this.getLongitude();
        return Math.sqrt(Math.pow(latDiff, 2) + Math.pow(longDiff, 2));
    }

    public double compareTo(double latitude, double longitude, Location other){
        double dist = this.getDistanceBetweenLocations(latitude, longitude);
        double otherDist = other.getDistanceBetweenLocations(latitude, longitude);
        return (dist - otherDist);
    }
}
