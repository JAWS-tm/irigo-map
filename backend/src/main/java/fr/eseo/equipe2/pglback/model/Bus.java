package fr.eseo.equipe2.pglback.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "buses")
public class Bus {

    // Vehicle park id (one per physical bus)
    @Id
    private Integer vehicleId;

    private String lineNb;

    private String lineName;

    private String destination;

    private Coordinate coordinates;

    private String nextStopName;

    private Date nextStopTime;

    private Integer delay;

    // Getters & Setters


    public Integer getVehicleId() {
        return vehicleId;
    }

    public Bus setVehicleId(Integer vehicleId) {
        this.vehicleId = vehicleId;
        return this;
    }

    public String getLineNb() {
        return lineNb;
    }

    public Bus setLineNb(String lineNb) {
        this.lineNb = lineNb;
        return this;
    }

    public String getLineName() {
        return lineName;
    }

    public Bus setLineName(String lineName) {
        this.lineName = lineName;
        return this;
    }

    public String getDestination() {
        return destination;
    }

    public Bus setDestination(String destination) {
        this.destination = destination;
        return this;
    }

    public Coordinate getCoordinates() {
        return coordinates;
    }

    public Bus setCoordinates(Coordinate coordinates) {
        this.coordinates = coordinates;
        return this;
    }

    public String getNextStopName() {
        return nextStopName;
    }

    public Bus setNextStopName(String nextStopName) {
        this.nextStopName = nextStopName;
        return this;
    }

    public Date getNextStopTime() {
        return nextStopTime;
    }

    public Bus setNextStopTime(Date nextStopTime) {
        this.nextStopTime = nextStopTime;
        return this;
    }

    public Integer getDelay() {
        return delay;
    }

    public Bus setDelay(Integer delay) {
        this.delay = delay;
        return this;
    }
}
