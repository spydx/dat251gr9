package no.hvl.dat251.gr9.lopbackend.entities.dto;

import lombok.Data;

@Data
public class LocationDTO {
    private String county;
    private String municipality;
    private String place;
    private double latitude;
    private double longitude;

    public LocationDTO(String county, String municipality, String place, double latitude, double longitude){
        this.county = county;
        this.municipality = municipality;
        this.place = place;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public LocationDTO(){

    }
}
