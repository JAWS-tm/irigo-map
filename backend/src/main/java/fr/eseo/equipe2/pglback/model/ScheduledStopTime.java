package fr.eseo.equipe2.pglback.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;

/**
 * Theoretical (scheduled) arrival time for a trip at a stop, from the static GTFS
 * feed's stop_times.txt. Used to compute real-time delay, since the real-time feed
 * only exposes a predicted absolute time, not an already-computed delay.
 */
@Entity
@Table(name = "scheduled_stop_times", indexes = @Index(name = "idx_scheduled_stop_times_trip_id", columnList = "tripId"))
public class ScheduledStopTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 32)
    private String tripId;

    @Column(nullable = false, length = 25)
    private String stopId;

    /**
     * Seconds since the service day's midnight. Per the GTFS spec this can exceed
     * 86400 for trips that continue past midnight.
     */
    @Column(nullable = false)
    private Integer scheduledArrivalSeconds;

    // Getters & Setters
    public Long getId() {
        return id;
    }

    public ScheduledStopTime setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTripId() {
        return tripId;
    }

    public ScheduledStopTime setTripId(String tripId) {
        this.tripId = tripId;
        return this;
    }

    public String getStopId() {
        return stopId;
    }

    public ScheduledStopTime setStopId(String stopId) {
        this.stopId = stopId;
        return this;
    }

    public Integer getScheduledArrivalSeconds() {
        return scheduledArrivalSeconds;
    }

    public ScheduledStopTime setScheduledArrivalSeconds(Integer scheduledArrivalSeconds) {
        this.scheduledArrivalSeconds = scheduledArrivalSeconds;
        return this;
    }
}
