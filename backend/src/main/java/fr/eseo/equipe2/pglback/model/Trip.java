package fr.eseo.equipe2.pglback.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "trips")
public class Trip {
    @Id
    @Column(length = 32)
    private String tripId;

    @Column(nullable = false)
    private String headsign;

    // Getters & Setters
    public String getTripId() {
        return tripId;
    }

    public Trip setTripId(String tripId) {
        this.tripId = tripId;
        return this;
    }

    public String getHeadsign() {
        return headsign;
    }

    public Trip setHeadsign(String headsign) {
        this.headsign = headsign;
        return this;
    }
}
