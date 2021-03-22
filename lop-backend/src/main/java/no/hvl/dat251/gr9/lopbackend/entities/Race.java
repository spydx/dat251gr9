package no.hvl.dat251.gr9.lopbackend.entities;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.GregorianCalendar;

@Entity
@Data
public class Race {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid2")
    private String id;

    private Float distance; //in Km
    private GregorianCalendar startTime; // in year, month, day, hours (0-23), minutes
    private Float elevation; //in meters
    private Boolean hillRun;
    private Boolean children;
    private Boolean womenOnly;
    private Boolean relay;
    private Boolean multiSport;
    private Boolean obstacleRun;
    private String info;

    @ManyToOne
    private Event event;

    public Race(Float distance, GregorianCalendar startTime, Float elevation, Boolean hillRun, Boolean children, Boolean womenOnly,
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

    public Race() {

    }
}
