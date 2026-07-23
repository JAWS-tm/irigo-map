package fr.eseo.equipe2.pglback.consumeApi;

import fr.eseo.equipe2.pglback.consumeApi.response.BusLineResponse;
import fr.eseo.equipe2.pglback.consumeApi.response.BusResponse;
import fr.eseo.equipe2.pglback.consumeApi.response.BusStopResponse;
import fr.eseo.equipe2.pglback.consumeApi.response.StopTimeResponse;
import fr.eseo.equipe2.pglback.dao.BusDao;
import fr.eseo.equipe2.pglback.dao.BusLineDao;
import fr.eseo.equipe2.pglback.dao.BusStopDao;
import fr.eseo.equipe2.pglback.model.Bus;
import fr.eseo.equipe2.pglback.model.BusLine;
import fr.eseo.equipe2.pglback.model.BusStop;
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
import java.util.stream.Collectors;


@Component
@EnableScheduling
public class IrigoApi {
    private static final Logger logger = LoggerFactory.getLogger(IrigoApi.class);

    private final String API_URL = "https://data.angers.fr/api/v2/catalog/datasets";

    @Autowired
    BusDao busDao;

    @Autowired
    BusStopDao busStopDao;

    @Autowired
    BusLineDao busLineDao;

    RestTemplate restTemplate;

    public IrigoApi() {
        restTemplate = new RestTemplate();
    }

    /**
     * Fetch buses every 30 seconds
     */
    @Scheduled(cron = "*/30 * * * * *")
    public void fetchAllBus() {
        try {
            String uri = API_URL + "/bus-tram-position-tr/exports/json";

            BusResponse[] buses = restTemplate.getForObject(uri, BusResponse[].class);

            if (buses == null) return;

            busDao.deleteAll();

            List<String> destBlacklist = Arrays.asList("TEST NOIR", "PAS EN SERVICE");

            List<Bus> busesEntities = Arrays.stream(buses)
                    .map(IrigoResponseMapper::toBusEntity)
                    .filter((bus) -> !destBlacklist.contains(bus.getDestination()))
                    .collect(Collectors.toList());

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

    public Optional<StopTimeResponse[]> fetchStopTimetable(String stopId) {
        try {
            String uri = API_URL + "/bus-tram-circulation-passages/exports/json?where=mnemoarret=\"" + stopId + "\"";

            StopTimeResponse[] stopTimetable = restTemplate.getForObject(uri, StopTimeResponse[].class);

            return Optional.ofNullable(stopTimetable);
        } catch (RestClientException e) {
            logger.error("fetchStopTimetable failed for stopId={}", stopId, e);
            return Optional.empty();
        }
    }
}
