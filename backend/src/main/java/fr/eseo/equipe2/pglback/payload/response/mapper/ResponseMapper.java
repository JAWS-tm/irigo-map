package fr.eseo.equipe2.pglback.payload.response.mapper;

import fr.eseo.equipe2.pglback.consumeApi.siri.SiriMonitoredStopVisit;
import fr.eseo.equipe2.pglback.model.*;
import fr.eseo.equipe2.pglback.payload.response.BusLineResponse;
import fr.eseo.equipe2.pglback.payload.response.BusResponse;
import fr.eseo.equipe2.pglback.payload.response.BusStopResponse;
import fr.eseo.equipe2.pglback.payload.response.StopTimetableResponse;
import fr.eseo.equipe2.pglback.payload.response.StopTimetableResponse.StopTime.TimeReliability;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Used to convert Model to Response Object
 */
public class ResponseMapper {

    public static BusResponse toBusResponse(Bus bus) {
        return new BusResponse().setVehicleId(bus.getVehicleId())
                .setLineNb(bus.getLineNb())
                .setLineName(bus.getLineName())
                .setCoordinates(bus.getCoordinates())
                .setDestination(bus.getDestination())
                .setNextStopName(bus.getNextStopName())
                .setNextStopTime(bus.getNextStopTime())
                .setDelay(bus.getDelay());
    }

    public static BusStopResponse toBusStopResponse(BusStop busStop) {
        return new BusStopResponse().setId(busStop.getId())
                .setName(busStop.getName())
                .setCoordinates(busStop.getCoordinates())
                .setWheelchairBoarding(busStop.getWheelchairBoarding());
    }

    public static BusLineResponse toBusLineResponse(BusLine busLine) {
        BusLineResponse line = new BusLineResponse().setLineId(busLine.getLineId())
                .setLineName(busLine.getLineName())
                .setLineColor(busLine.getLineColor());

        List<List<Coordinate>> routesCoordinates = new ArrayList<>();
        // get coordinates list ordered

        for (int i = 0; i < 20; i++) {
            routesCoordinates.add(new ArrayList<>());
        }

        for (LinePoint linePoint : busLine.getLinePoints()) {
            routesCoordinates.get(linePoint.getRouteGroup()).add(linePoint.getCoordinate());
        }
        line.setCoordinates(routesCoordinates);

        return line;
    }

    public static StopTimetableResponse toStopTimetableResponse(List<SiriMonitoredStopVisit> visits) {
        List<StopTimetableResponse.StopTime> timetable = visits.stream()
                .map(visit -> new StopTimetableResponse.StopTime()
                        .setLineId(visit.lineRef())
                        .setLineCodeName(visit.journeyPatternName())
                        .setDestination(visit.destinationName())
                        .setTheoreticalArrival(toDate(visit.aimedArrival()))
                        .setTheoreticalDeparture(toDate(visit.aimedDeparture()))
                        .setArrivalTime(toDate(visit.expectedArrival()))
                        .setDepartureTime(toDate(visit.expectedDeparture()))
                        .setReliability(visit.expectedArrival() != null ? TimeReliability.RELIABLE : TimeReliability.THEORETICAL)
                )
                .collect(Collectors.toList());
        return new StopTimetableResponse().setTimetable(timetable);
    }

    private static Date toDate(Instant instant) {
        return instant == null ? null : Date.from(instant);
    }

}
