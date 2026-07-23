package fr.eseo.equipe2.pglback.consumeApi.siri;

import java.util.List;

/**
 * Fetches real-time passage predictions for a single stop from Irigo's SIRI
 * StopMonitoring service. Implementations must translate any transport or parsing
 * failure into a {@link org.springframework.web.client.RestClientException} so callers
 * only need to handle one exception type.
 */
public interface SiriStopMonitoringClient {
    List<SiriMonitoredStopVisit> fetchStopMonitoring(String stopId);
}
