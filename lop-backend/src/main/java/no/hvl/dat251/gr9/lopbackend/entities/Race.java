package no.hvl.dat251.gr9.lopbackend.entities;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
public class Race {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid2")
    private String id;

    private Float distance;
    private Date starttime;
    private Float elevation;
    private Boolean hillRun;
    private Boolean children;
    private Boolean womenOnly;
    private Boolean relay;
    private Boolean multiSport;
    private Boolean obstacleRun;
    private String info;


}
