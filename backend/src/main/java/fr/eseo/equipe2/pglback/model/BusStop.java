package fr.eseo.equipe2.pglback.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "bus_stops")
public class BusStop {
    @Id
    private String id;

    @Column(nullable = false)
    private String name;

    @Embedded
    private Coordinate coordinates;

    @Column(nullable = false)
    private Boolean wheelchairBoarding;


    // Getters & Setters
    public String getId() {
        return id;
    }

    public BusStop setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public BusStop setName(String name) {
        this.name = name;
        return this;
    }

    public Coordinate getCoordinates() {
        return coordinates;
    }

    public BusStop setCoordinates(Coordinate coordinates) {
        this.coordinates = coordinates;
        return this;
    }

    public Boolean getWheelchairBoarding() {
        return wheelchairBoarding;
    }

    public BusStop setWheelchairBoarding(Boolean wheelchairBoarding) {
        this.wheelchairBoarding = wheelchairBoarding;
        return this;
    }
}
