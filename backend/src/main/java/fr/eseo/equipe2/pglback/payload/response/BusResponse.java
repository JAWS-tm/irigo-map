package fr.eseo.equipe2.pglback.payload.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import fr.eseo.equipe2.pglback.model.Coordinate;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class BusResponse {
    private Integer vehicleId;

    private String lineNb;

    private String lineName;

    private String destination;

    private Coordinate coordinates;

    private String nextStopName;

    private Date nextStopTime;

    private Integer delay;

    public BusResponse() {
    }

    public Integer getVehicleId() {
        return vehicleId;
    }

    public BusResponse setVehicleId(Integer vehicleId) {
        this.vehicleId = vehicleId;
        return this;
    }

    public String getLineNb() {
        return lineNb;
    }

    public BusResponse setLineNb(String lineNb) {
        this.lineNb = lineNb;
        return this;
    }

    public String getLineName() {
        return lineName;
    }

    public BusResponse setLineName(String lineName) {
        this.lineName = lineName;
        return this;
    }

    public String getDestination() {
        return destination;
    }

    public BusResponse setDestination(String destination) {
        this.destination = destination;
        return this;
    }

    public Coordinate getCoordinates() {
        return coordinates;
    }

    public BusResponse setCoordinates(Coordinate coordinates) {
        this.coordinates = coordinates;
        return this;
    }

    public String getNextStopName() {
        return nextStopName;
    }

    public BusResponse setNextStopName(String nextStopName) {
        this.nextStopName = nextStopName;
        return this;
    }

    public Date getNextStopTime() {
        return nextStopTime;
    }

    public BusResponse setNextStopTime(Date nextStopTime) {
        this.nextStopTime = nextStopTime;
        return this;
    }

    public Integer getDelay() {
        return delay;
    }

    public BusResponse setDelay(Integer delay) {
        this.delay = delay;
        return this;
    }
}
