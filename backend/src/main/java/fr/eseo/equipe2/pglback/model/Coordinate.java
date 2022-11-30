package fr.eseo.equipe2.pglback.model;

import org.springframework.lang.Nullable;

import javax.persistence.Embeddable;

@Embeddable
public class Coordinate {
    private Double lat;
    private Double lon;

    public Coordinate() {}

    public Coordinate(Double lat, Double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public Double getLat() {
        return lat;
    }

    public Coordinate setLat(Double lat) {
        this.lat = lat;
        return this;
    }

    public Double getLon() {
        return lon;
    }

    public Coordinate setLon(Double lon) {
        this.lon = lon;
        return this;
    }
}
