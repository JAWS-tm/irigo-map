package fr.eseo.equipe2.pglback.payload.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class StopTimetableResponse {

    private String stopId;
    private List<StopTime> timetable;

    public static class StopTime {
        private String stopId;
        private Date theoreticalArrival;
        private Date theoreticalDeparture;
        private Date arrivalTime;
        private Date departureTime;
        // reliability of arrival & departure : THEORICAL or RELIABLE (real time)
        private TimeReliability reliability;
        private String lineId;
        private String destination;
        private String lineCodeName;

        public enum TimeReliability {
            THEORETICAL, RELIABLE
        }

        public StopTime() {
        }

        public String getStopId() {
            return stopId;
        }

        public StopTime setStopId(String stopId) {
            this.stopId = stopId;
            return this;
        }

        public Date getTheoreticalArrival() {
            return theoreticalArrival;
        }

        public StopTime setTheoreticalArrival(Date theoreticalArrival) {
            this.theoreticalArrival = theoreticalArrival;
            return this;
        }

        public Date getTheoreticalDeparture() {
            return theoreticalDeparture;
        }

        public StopTime setTheoreticalDeparture(Date theoreticalDeparture) {
            this.theoreticalDeparture = theoreticalDeparture;
            return this;
        }

        public Date getArrivalTime() {
            return arrivalTime;
        }

        public StopTime setArrivalTime(Date arrivalTime) {
            this.arrivalTime = arrivalTime;
            return this;
        }

        public Date getDepartureTime() {
            return departureTime;
        }

        public StopTime setDepartureTime(Date departureTime) {
            this.departureTime = departureTime;
            return this;
        }

        public TimeReliability getReliability() {
            return reliability;
        }

        public StopTime setReliability(TimeReliability reliability) {
            this.reliability = reliability;
            return this;
        }

        public String getLineId() {
            return lineId;
        }

        public StopTime setLineId(String lineId) {
            this.lineId = lineId;
            return this;
        }

        public String getDestination() {
            return destination;
        }

        public StopTime setDestination(String destination) {
            this.destination = destination;
            return this;
        }

        public String getLineCodeName() {
            return lineCodeName;
        }

        public StopTime setLineCodeName(String lineCodeName) {
            this.lineCodeName = lineCodeName;
            return this;
        }
    }

    public StopTimetableResponse() {
    }

    public String getStopId() {
        return stopId;
    }

    public StopTimetableResponse setStopId(String stopId) {
        this.stopId = stopId;
        return this;
    }

    public List<StopTime> getTimetable() {
        return timetable;
    }

    public StopTimetableResponse setTimetable(List<StopTime> timetable) {
        this.timetable = timetable;
        return this;
    }
}
