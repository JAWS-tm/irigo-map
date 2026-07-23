package fr.eseo.equipe2.pglback.consumeApi.gtfs;

/**
 * Fetches trip metadata (destination headsign) and theoretical stop times from Irigo's
 * static GTFS feed. Implementations must translate any transport or parsing failure into
 * a {@link org.springframework.web.client.RestClientException} so callers only need to
 * handle one exception type.
 */
public interface GtfsStaticFeedClient {
    GtfsStaticFeed fetchStaticFeed();
}
