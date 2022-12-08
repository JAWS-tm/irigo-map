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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Component
@EnableScheduling
public class IrigoApi {
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

    @Scheduled(cron = "*/30 * * * * *")
    public void fetchAllBus() {
        String uri = API_URL + "/bus-tram-position-tr/exports/json";

        BusResponse[] buses = restTemplate.getForObject(uri, BusResponse[].class);

        if (buses == null) return;

        List<Bus> busesEntities = Arrays.stream(buses)
                .map(IrigoResponseMapper::toBusEntity)
                .collect(Collectors.toList());

        busDao.saveAll(busesEntities);
    }

    @Scheduled(cron = "@weekly")
    public void fetchAllStop() {
        String uri = API_URL + "/horaires-theoriques-et-arrets-du-reseau-irigo-gtfs/exports/json";

        BusStopResponse[] busStops = restTemplate.getForObject(uri, BusStopResponse[].class);

        if (busStops == null) return;

        List<BusStop> stopsEntities = Arrays.stream(busStops)
                .map(IrigoResponseMapper::toBusStopEntity)
                .collect(Collectors.toList());

        busStopDao.saveAll(stopsEntities);
    }

    @Scheduled(cron = "@monthly")
    public void fetchBusLines() {
        String uri = API_URL + "/irigo_gtfs_lines/exports/json?where=route_short_name in (\"01\", \"02\", \"03\", \"04\", \"05\", \"06\", \"07\", \"08\", \"09\", \"10\", \"11\", \"12\")";

        BusLineResponse[] busLines = restTemplate.getForObject(uri, BusLineResponse[].class);

        if (busLines == null) return;

        busLineDao.deleteAll();

        List<BusLine> linesEntities = Arrays.stream(busLines)
                .map(IrigoResponseMapper::toBusLineEntity)
                .collect(Collectors.toList());

        busLineDao.saveAll(linesEntities);
    }

    public Optional<StopTimeResponse[]> fetchStopTimetable(String stopId) {
        String uri = API_URL + "/bus-tram-circulation-passages/exports/json?where=mnemoarret=\""+stopId+"\"";

        StopTimeResponse[] stopTimetable = restTemplate.getForObject(uri, StopTimeResponse[].class);

        return Optional.ofNullable(stopTimetable);
    }
}
