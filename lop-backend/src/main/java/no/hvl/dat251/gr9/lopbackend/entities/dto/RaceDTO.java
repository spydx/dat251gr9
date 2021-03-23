package no.hvl.dat251.gr9.lopbackend.entities.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.Date;
import java.util.GregorianCalendar;

@Data
public class RaceDTO {
    private Float distance;
    private LocalDate startTime;
    private Float elevation;
    private Boolean hillRun;
    private Boolean children;
    private Boolean womenOnly;
    private Boolean relay;
    private Boolean multiSport;
    private Boolean obstacleRun;
    private String info;

    public RaceDTO(Float distance, LocalDate startTime, Float elevation, Boolean hillRun, Boolean children, Boolean womenOnly,
                   Boolean relay, Boolean multiSport, Boolean obstacleRun, String info) {
        this.distance = distance;
        this.startTime = startTime;
        this.elevation = elevation;
        this.hillRun = hillRun;
        this.children = children;
        this.womenOnly = womenOnly;
        this.relay = relay;
        this.multiSport = multiSport;
        this.obstacleRun = obstacleRun;
        this.info = info;
    }

    public RaceDTO(){}
}
