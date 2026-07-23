package fr.eseo.equipe2.pglback.consumeApi.siri;

import java.time.Instant;

/**
 * One predicted passage at a monitored stop. SIRI keeps the theoretical ("Aimed") and
 * predicted ("Expected") times separate, so both are exposed as-is; a null "expected"
 * value means no real-time prediction is currently available for that passage.
 */
public record SiriMonitoredStopVisit(
        String lineRef,
        String journeyPatternName,
        String destinationName,
        Instant aimedArrival,
        Instant expectedArrival,
        Instant aimedDeparture,
        Instant expectedDeparture
) {
}
