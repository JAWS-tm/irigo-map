package fr.eseo.equipe2.pglback.consumeApi;

import com.google.transit.realtime.GtfsRealtime.FeedMessage;
import fr.eseo.equipe2.pglback.consumeApi.gtfs.GtfsRealtimeClient;
import fr.eseo.equipe2.pglback.consumeApi.gtfs.GtfsStaticFeed;
import fr.eseo.equipe2.pglback.consumeApi.gtfs.GtfsStaticFeedClient;
import fr.eseo.equipe2.pglback.consumeApi.gtfs.TripStopKey;
import fr.eseo.equipe2.pglback.consumeApi.response.BusLineResponse;
import fr.eseo.equipe2.pglback.consumeApi.response.BusStopResponse;
import fr.eseo.equipe2.pglback.consumeApi.siri.SiriMonitoredStopVisit;
import fr.eseo.equipe2.pglback.consumeApi.siri.SiriStopMonitoringClient;
import fr.eseo.equipe2.pglback.dao.BusDao;
import fr.eseo.equipe2.pglback.dao.BusLineDao;
import fr.eseo.equipe2.pglback.dao.BusStopDao;
import fr.eseo.equipe2.pglback.dao.ScheduledStopTimeDao;
import fr.eseo.equipe2.pglback.dao.TripDao;
import fr.eseo.equipe2.pglback.model.Bus;
import fr.eseo.equipe2.pglback.model.BusLine;
import fr.eseo.equipe2.pglback.model.BusStop;
import fr.eseo.equipe2.pglback.model.ScheduledStopTime;
import fr.eseo.equipe2.pglback.model.Trip;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Component
@EnableScheduling
public class IrigoApi {
    private static final Logger logger = LoggerFactory.getLogger(IrigoApi.class);
    private static final String API_URL = "https://data.angers.fr/api/v2/catalog/datasets";

    private final BusDao busDao;
    private final BusStopDao busStopDao;
    private final BusLineDao busLineDao;
    private final TripDao tripDao;
    private final ScheduledStopTimeDao scheduledStopTimeDao;
    private final RestTemplate restTemplate;
    private final GtfsRealtimeClient gtfsRealtimeClient;
    private final GtfsStaticFeedClient gtfsStaticFeedClient;
    private final SiriStopMonitoringClient siriStopMonitoringClient;

    @Autowired
    public IrigoApi(BusDao busDao,
                     BusStopDao busStopDao,
                     BusLineDao busLineDao,
                     TripDao tripDao,
                     ScheduledStopTimeDao scheduledStopTimeDao,
                     RestTemplate restTemplate,
                     GtfsRealtimeClient gtfsRealtimeClient,
                     GtfsStaticFeedClient gtfsStaticFeedClient,
                     SiriStopMonitoringClient siriStopMonitoringClient) {
        this.busDao = busDao;
        this.busStopDao = busStopDao;
        this.busLineDao = busLineDao;
        this.tripDao = tripDao;
        this.scheduledStopTimeDao = scheduledStopTimeDao;
        this.restTemplate = restTemplate;
        this.gtfsRealtimeClient = gtfsRealtimeClient;
        this.gtfsStaticFeedClient = gtfsStaticFeedClient;
        this.siriStopMonitoringClient = siriStopMonitoringClient;
    }

    /**
     * Fetch buses every 30 seconds.
     * Positions and predicted times come from two GTFS-RT feeds; line/stop/destination
     * names and theoretical times come from tables we already populate from GTFS static
     * data, since all of these share the same id namespace.
     */
    @Scheduled(cron = "*/30 * * * * *")
    public void fetchAllBus() {
        try {
            FeedMessage vehiclePositions = gtfsRealtimeClient.fetchVehiclePositions();
            FeedMessage tripUpdates = gtfsRealtimeClient.fetchTripUpdates();

            Map<String, String> lineNameById = busLineDao.findAll().stream()
                    .collect(Collectors.toMap(BusLine::getLineId, BusLine::getLineName));
            Map<String, String> stopNameById = busStopDao.findAll().stream()
                    .collect(Collectors.toMap(BusStop::getId, BusStop::getName));
            Map<String, String> headsignByTripId = tripDao.findAll().stream()
                    .collect(Collectors.toMap(Trip::getTripId, Trip::getHeadsign));

            Set<String> activeTripIds = tripUpdates.getEntityList().stream()
                    .filter(entity -> entity.hasTripUpdate())
                    .map(entity -> entity.getTripUpdate().getTrip().getTripId())
                    .collect(Collectors.toSet());
            List<ScheduledStopTime> relevantScheduledStopTimes = scheduledStopTimeDao.findByTripIdIn(activeTripIds);
            Map<TripStopKey, Integer> delayByTripAndStop = IrigoResponseMapper.toDelaysByTripAndStop(tripUpdates, relevantScheduledStopTimes);

            List<Bus> busesEntities = vehiclePositions.getEntityList().stream()
                    .filter(entity -> entity.hasVehicle() && entity.getVehicle().hasPosition())
                    .map(entity -> IrigoResponseMapper.toBusEntity(entity.getVehicle(), lineNameById, stopNameById, headsignByTripId, delayByTripAndStop))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toList());

            busDao.deleteAll();
            busDao.saveAll(busesEntities);
        } catch (RestClientException e) {
            logger.error("fetchAllBus failed, keeping previous data", e);
        }
    }

    @Scheduled(cron = "@weekly")
    public void fetchAllStop() {
        try {
            String uri = API_URL + "/stop_route_irigo_gtfs/exports/json";

            BusStopResponse[] busStops = restTemplate.getForObject(uri, BusStopResponse[].class);

            if (busStops == null) return;

            busStopDao.deleteAll();

            // The dataset has one row per (stop, line) pair, so the same stop appears multiple times.
            Map<String, BusStop> stopsById = new LinkedHashMap<>();
            for (BusStopResponse busStopRes : busStops) {
                BusStop stop = IrigoResponseMapper.toBusStopEntity(busStopRes);
                stopsById.put(stop.getId(), stop);
            }

            busStopDao.saveAll(stopsById.values());
        } catch (RestClientException e) {
            logger.error("fetchAllStop failed, keeping previous data", e);
        }
    }

    @Scheduled(cron = "@monthly")
    public void fetchBusLines() {
        try {
            String uri = API_URL + "/irigo_gtfs_lines/exports/json?where=route_short_name in (\"1\", \"2\", \"3\", \"4\", \"5\", \"6\", \"7\", \"8\", \"9\", \"10\", \"11\", \"12\")";

            BusLineResponse[] busLines = restTemplate.getForObject(uri, BusLineResponse[].class);

            if (busLines == null) return;

            busLineDao.deleteAll();

            List<BusLine> linesEntities = Arrays.stream(busLines)
                    .map(IrigoResponseMapper::toBusLineEntity)
                    .collect(Collectors.toList());

            busLineDao.saveAll(linesEntities);
        } catch (RestClientException e) {
            logger.error("fetchBusLines failed, keeping previous data", e);
        }
    }

    /**
     * Trip destinations and theoretical stop times only change when the schedule itself
     * changes, so a weekly refresh from the GTFS static feed is enough - same cadence as
     * fetchAllStop.
     */
    @Scheduled(cron = "@weekly")
    public void fetchStaticSchedule() {
        try {
            GtfsStaticFeed feed = gtfsStaticFeedClient.fetchStaticFeed();

            tripDao.deleteAll();
            tripDao.saveAll(feed.trips());

            scheduledStopTimeDao.deleteAll();
            scheduledStopTimeDao.saveAll(feed.scheduledStopTimes());
        } catch (RestClientException e) {
            logger.error("fetchStaticSchedule failed, keeping previous data", e);
        }
    }

    public Optional<List<SiriMonitoredStopVisit>> fetchStopTimetable(String stopId) {
        try {
            return Optional.of(siriStopMonitoringClient.fetchStopMonitoring(stopId));
        } catch (RestClientException e) {
            logger.error("fetchStopTimetable failed for stopId={}", stopId, e);
            return Optional.empty();
        }
    }
}
