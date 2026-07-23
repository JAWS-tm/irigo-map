package fr.eseo.equipe2.pglback.consumeApi.gtfs;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.transit.realtime.GtfsRealtime.FeedMessage;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
public class GtfsRealtimeHttpClient implements GtfsRealtimeClient {
    private static final String VEHICLE_POSITIONS_URL = "https://ara-api.enroute.mobi/irigo/gtfs/vehicle-positions";
    private static final String TRIP_UPDATES_URL = "https://ara-api.enroute.mobi/irigo/gtfs/trip-updates";

    private final RestTemplate restTemplate;

    public GtfsRealtimeHttpClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public FeedMessage fetchVehiclePositions() {
        return fetchFeed(VEHICLE_POSITIONS_URL);
    }

    @Override
    public FeedMessage fetchTripUpdates() {
        return fetchFeed(TRIP_UPDATES_URL);
    }

    private FeedMessage fetchFeed(String url) {
        byte[] data = restTemplate.getForObject(url, byte[].class);
        if (data == null) {
            throw new RestClientException("Empty GTFS-RT response from " + url);
        }
        try {
            return FeedMessage.parseFrom(data);
        } catch (InvalidProtocolBufferException e) {
            throw new RestClientException("Malformed GTFS-RT payload from " + url, e);
        }
    }
}
