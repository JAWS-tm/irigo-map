package fr.eseo.equipe2.pglback.consumeApi.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import fr.eseo.equipe2.pglback.model.Coordinate;
import io.swagger.models.auth.In;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BusStopResponse {
    private String stop_id;
    private String stop_code;
    private String stop_name;
    private Coordinate stop_coordinates;
    private Integer wheelchair_boarding;


    // Getters & Setters
    public String getStop_id() {
        return stop_id;
    }

    public BusStopResponse setStop_id(String stop_id) {
        this.stop_id = stop_id;
        return this;
    }

    public String getStop_code() {
        return stop_code;
    }

    public BusStopResponse setStop_code(String stop_code) {
        this.stop_code = stop_code;
        return this;
    }

    public String getStop_name() {
        return stop_name;
    }

    public BusStopResponse setStop_name(String stop_name) {
        this.stop_name = stop_name;
        return this;
    }

    public Coordinate getStop_coordinates() {
        return stop_coordinates;
    }

    public BusStopResponse setStop_coordinates(Coordinate stop_coordinates) {
        this.stop_coordinates = stop_coordinates;
        return this;
    }

    public Integer getWheelchair_boarding() {
        return wheelchair_boarding;
    }

    public BusStopResponse setWheelchair_boarding(Integer wheelchair_boarding) {
        this.wheelchair_boarding = wheelchair_boarding;
        return this;
    }
}
