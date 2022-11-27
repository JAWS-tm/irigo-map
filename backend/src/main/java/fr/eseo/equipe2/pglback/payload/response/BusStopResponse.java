package fr.eseo.equipe2.pglback.payload.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import fr.eseo.equipe2.pglback.model.Coordinate;

import javax.persistence.Column;
import javax.persistence.Embedded;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class BusStopResponse {
    private String id;

    private String name;

    private Coordinate coordinates;

    private Boolean wheelchairBoarding;

    public BusStopResponse() {
    }

    public String getId() {
        return id;
    }

    public BusStopResponse setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public BusStopResponse setName(String name) {
        this.name = name;
        return this;
    }

    public Coordinate getCoordinates() {
        return coordinates;
    }

    public BusStopResponse setCoordinates(Coordinate coordinates) {
        this.coordinates = coordinates;
        return this;
    }

    public Boolean getWheelchairBoarding() {
        return wheelchairBoarding;
    }

    public BusStopResponse setWheelchairBoarding(Boolean wheelchairBoarding) {
        this.wheelchairBoarding = wheelchairBoarding;
        return this;
    }
}
