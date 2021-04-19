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

    /*
    Finds the distance in km between two points on earth using Haversine formula
     */
    public double getDistanceBetweenLocations(double latitude, double longitude){
        double earthRadius = 6371.009; // radius median in km
        double latDiff = Math.toRadians(latitude - this.getLatitude());
        double longDiff = Math.toRadians(longitude - this.getLongitude());
        double lat1Rad = Math.toRadians(this.latitude);
        double lat2Rad = Math.toRadians(latitude);
        double exp1 = Math.pow(Math.sin(latDiff/2), 2);
        double exp2 = Math.pow(Math.sin(longDiff/2), 2);
        double exp3 = Math.cos(lat1Rad)*Math.cos(lat2Rad)*exp2;
        double exp4 = Math.sqrt(exp1 + exp3);
        return (2 * earthRadius * Math.asin(exp4));
    }

    public double compareTo(double latitude, double longitude, Location other){
        double dist = this.getDistanceBetweenLocations(latitude, longitude);
        double otherDist = other.getDistanceBetweenLocations(latitude, longitude);
        return (dist - otherDist);
    }
}
