package fr.eseo.equipe2.pglback.consumeApi;

import com.google.transit.realtime.GtfsRealtime.FeedEntity;
import com.google.transit.realtime.GtfsRealtime.FeedMessage;
import com.google.transit.realtime.GtfsRealtime.TripUpdate;
import com.google.transit.realtime.GtfsRealtime.TripUpdate.StopTimeUpdate;
import com.google.transit.realtime.GtfsRealtime.VehiclePosition;
import fr.eseo.equipe2.pglback.consumeApi.gtfs.TripStopKey;
import fr.eseo.equipe2.pglback.consumeApi.response.BusLineResponse;
import fr.eseo.equipe2.pglback.consumeApi.response.BusStopResponse;
import fr.eseo.equipe2.pglback.model.*;

import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class IrigoResponseMapper {
    /**
     * The vehicle-positions feed only carries route/stop/trip ids, not display names.
     * lineName/nextStopName come from the GTFS static lookup maps we already maintain;
     * destination/delay come from the trip headsign lookup and the trip-updates feed,
     * keyed by trip (and, for delay, the specific stop being approached).
     */
    public static Optional<Bus> toBusEntity(VehiclePosition vehicle,
                                             Map<String, String> lineNameById,
                                             Map<String, String> stopNameById,
                                             Map<String, String> headsignByTripId,
                                             Map<TripStopKey, Integer> delayByTripAndStop) {
        Integer vehicleId;
        try {
            vehicleId = Integer.valueOf(vehicle.getVehicle().getId());
        } catch (NumberFormatException e) {
            return Optional.empty();
        }

        String routeId = vehicle.getTrip().getRouteId();
        String tripId = vehicle.getTrip().getTripId();
        String stopId = vehicle.getStopId();

        // fetchBusLines() only tracks a fixed set of lines; skip vehicles running on any
        // other line rather than showing them with a blank name.
        if (!lineNameById.containsKey(routeId)) {
            return Optional.empty();
        }

        Bus bus = new Bus().setVehicleId(vehicleId)
                .setLineNb(routeId)
                .setLineName(lineNameById.get(routeId))
                .setDestination(headsignByTripId.get(tripId))
                .setDelay(delayByTripAndStop.get(new TripStopKey(tripId, stopId)))
                .setCoordinates(new Coordinate((double) vehicle.getPosition().getLatitude(), (double) vehicle.getPosition().getLongitude()))
                .setNextStopName(stopNameById.get(stopId));

        return Optional.of(bus);
    }

    private static final ZoneId SERVICE_TIME_ZONE = ZoneId.of("Europe/Paris");

    /**
     * Irigo's trip-updates feed exposes a predicted absolute arrival time, not a
     * ready-made delay, so we compute it ourselves against the theoretical time from
     * the static feed's stop_times.txt: delay = predicted time - (service day midnight +
     * scheduled seconds since midnight).
     * <p>
     * The service day is derived from the feed's own timestamp, which is correct for the
     * vast majority of trips; it can be off by one day for a trip still running between
     * midnight and ~4am that was scheduled using GTFS's ">24h" notation for the previous
     * service day - a known, generally accepted ambiguity in GTFS real-time feeds.
     */
    public static Map<TripStopKey, Integer> toDelaysByTripAndStop(FeedMessage tripUpdates, List<ScheduledStopTime> scheduledStopTimes) {
        long serviceMidnightEpochSeconds = Instant.ofEpochSecond(tripUpdates.getHeader().getTimestamp())
                .atZone(SERVICE_TIME_ZONE)
                .toLocalDate()
                .atStartOfDay(SERVICE_TIME_ZONE)
                .toEpochSecond();

        Map<TripStopKey, Integer> scheduledSecondsByTripStop = scheduledStopTimes.stream()
                .collect(Collectors.toMap(
                        s -> new TripStopKey(s.getTripId(), s.getStopId()),
                        ScheduledStopTime::getScheduledArrivalSeconds,
                        (first, second) -> first));

        Map<TripStopKey, Integer> delays = new HashMap<>();

        for (FeedEntity entity : tripUpdates.getEntityList()) {
            if (!entity.hasTripUpdate()) continue;

            TripUpdate tripUpdate = entity.getTripUpdate();
            String tripId = tripUpdate.getTrip().getTripId();

            for (StopTimeUpdate stopTimeUpdate : tripUpdate.getStopTimeUpdateList()) {
                if (!stopTimeUpdate.hasArrival() || !stopTimeUpdate.getArrival().hasTime()) continue;

                TripStopKey key = new TripStopKey(tripId, stopTimeUpdate.getStopId());
                Integer scheduledSeconds = scheduledSecondsByTripStop.get(key);
                if (scheduledSeconds == null) continue;

                long expectedEpochSeconds = serviceMidnightEpochSeconds + scheduledSeconds;
                long predictedEpochSeconds = stopTimeUpdate.getArrival().getTime();

                delays.put(key, (int) (predictedEpochSeconds - expectedEpochSeconds));
            }
        }

        return delays;
    }

    public static BusStop toBusStopEntity(BusStopResponse busStopRes) {
        return new BusStop().setId(busStopRes.getId())
                .setName(busStopRes.getName())
                .setCoordinates(busStopRes.getCoordinates())
                .setWheelchairBoarding(Objects.equals(busStopRes.getAccessibility(), 1));
    }

    public static BusLine toBusLineEntity(BusLineResponse busLineRes) {
        BusLine busLine = new BusLine().setLineId(busLineRes.getRoute_id())
                .setLineColor(busLineRes.getRoute_color())
                .setLineName(busLineRes.getRoute_long_name());

        List<LinePoint> linePoints = new ArrayList<>();
        Integer orderId = 0;
        Integer groupId = 0;
        for (List<List<Double>> routes : busLineRes.getShape().getGeometry().getCoordinates()) {
            for (List<Double> routePoints : routes) {
                Coordinate coord = new Coordinate(routePoints.get(1), routePoints.get(0));
                linePoints.add(new LinePoint(orderId, groupId, coord, busLine));
                orderId++;
            }
            groupId++;
        }

        busLine.setLinePoints(linePoints);

        return busLine;
    }
}
