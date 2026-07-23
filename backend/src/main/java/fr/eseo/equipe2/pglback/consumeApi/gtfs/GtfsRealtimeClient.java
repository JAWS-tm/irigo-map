package fr.eseo.equipe2.pglback.consumeApi.gtfs;

import com.google.transit.realtime.GtfsRealtime.FeedMessage;

/**
 * Talks to Irigo's GTFS-RT feeds. Implementations must translate any transport or
 * parsing failure into a {@link org.springframework.web.client.RestClientException} so
 * callers only need to handle one exception type.
 */
public interface GtfsRealtimeClient {
    FeedMessage fetchVehiclePositions();

    FeedMessage fetchTripUpdates();
}
