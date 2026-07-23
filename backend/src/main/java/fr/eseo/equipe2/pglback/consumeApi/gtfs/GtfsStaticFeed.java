package fr.eseo.equipe2.pglback.consumeApi.gtfs;

import fr.eseo.equipe2.pglback.model.ScheduledStopTime;
import fr.eseo.equipe2.pglback.model.Trip;

import java.util.List;

public record GtfsStaticFeed(List<Trip> trips, List<ScheduledStopTime> scheduledStopTimes) {
}
