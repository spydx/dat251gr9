package no.hvl.dat251.gr9.lopbackend.entities.dto;

import lombok.Data;


import java.time.LocalTime;

@Data
public class RaceDTO {
    private Float distance;
    private LocalTime startTime;
    private Float elevation;
    private Boolean hillRun;
    private Boolean children;
    private Boolean womenOnly;
    private Boolean relay;
    private Boolean multiSport;
    private Boolean obstacleRun;
    private String info;
    private int participants;

    public RaceDTO(Float distance, LocalTime startTime, Float elevation, Boolean hillRun, Boolean children, Boolean womenOnly,
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
        this.participants = 0;
    }

    public RaceDTO(){}
}
