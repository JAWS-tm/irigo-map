package fr.eseo.equipe2.pglback.consumeApi.gtfs;

import fr.eseo.equipe2.pglback.model.ScheduledStopTime;
import fr.eseo.equipe2.pglback.model.Trip;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Component
public class GtfsStaticFeedHttpClient implements GtfsStaticFeedClient {
    private static final String GTFS_ZIP_URL = "https://chouette.enroute.mobi/api/v1/datas/Irigo/gtfs.zip";
    private static final String TRIPS_FILE_NAME = "trips.txt";
    private static final String STOP_TIMES_FILE_NAME = "stop_times.txt";

    private final RestTemplate restTemplate;

    public GtfsStaticFeedHttpClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public GtfsStaticFeed fetchStaticFeed() {
        byte[] zipData = restTemplate.getForObject(GTFS_ZIP_URL, byte[].class);
        if (zipData == null) {
            throw new RestClientException("Empty GTFS static feed response from " + GTFS_ZIP_URL);
        }
        return extractFeed(zipData);
    }

    private GtfsStaticFeed extractFeed(byte[] zipData) {
        List<Trip> trips = null;
        List<ScheduledStopTime> scheduledStopTimes = null;

        try (ZipInputStream zip = new ZipInputStream(new ByteArrayInputStream(zipData))) {
            ZipEntry entry;
            while ((entry = zip.getNextEntry()) != null) {
                // Read the whole entry up front so each parser gets its own independent
                // stream - closing it can't accidentally close the shared ZipInputStream.
                if (entry.getName().equals(TRIPS_FILE_NAME)) {
                    trips = parseTripsCsv(new ByteArrayInputStream(zip.readAllBytes()));
                } else if (entry.getName().equals(STOP_TIMES_FILE_NAME)) {
                    scheduledStopTimes = parseStopTimesCsv(new ByteArrayInputStream(zip.readAllBytes()));
                }
            }
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to read GTFS static feed zip", e);
        }

        if (trips == null) {
            throw new IllegalStateException(TRIPS_FILE_NAME + " not found in GTFS static feed");
        }
        if (scheduledStopTimes == null) {
            throw new IllegalStateException(STOP_TIMES_FILE_NAME + " not found in GTFS static feed");
        }

        return new GtfsStaticFeed(trips, scheduledStopTimes);
    }

    private List<Trip> parseTripsCsv(InputStream tripsCsv) throws IOException {
        List<Trip> trips = new ArrayList<>();
        CSVFormat format = CSVFormat.DEFAULT.builder().setHeader().setSkipHeaderRecord(true).build();

        try (CSVParser parser = format.parse(new InputStreamReader(tripsCsv, StandardCharsets.UTF_8))) {
            for (CSVRecord record : parser) {
                trips.add(new Trip()
                        .setTripId(record.get("trip_id"))
                        .setHeadsign(record.get("trip_headsign")));
            }
        }

        return trips;
    }

    private List<ScheduledStopTime> parseStopTimesCsv(InputStream stopTimesCsv) throws IOException {
        List<ScheduledStopTime> stopTimes = new ArrayList<>();
        CSVFormat format = CSVFormat.DEFAULT.builder().setHeader().setSkipHeaderRecord(true).build();

        try (CSVParser parser = format.parse(new InputStreamReader(stopTimesCsv, StandardCharsets.UTF_8))) {
            for (CSVRecord record : parser) {
                parseSecondsSinceMidnight(record.get("arrival_time")).ifPresent(seconds ->
                        stopTimes.add(new ScheduledStopTime()
                                .setTripId(record.get("trip_id"))
                                .setStopId(record.get("stop_id"))
                                .setScheduledArrivalSeconds(seconds)));
            }
        }

        return stopTimes;
    }

    /**
     * GTFS times are "HH:MM:SS" and can exceed 24:00:00 for trips continuing past
     * midnight - that's intentional, not a bug, so we keep the raw value.
     */
    private Optional<Integer> parseSecondsSinceMidnight(String gtfsTime) {
        if (gtfsTime == null || gtfsTime.isBlank()) {
            return Optional.empty();
        }

        String[] parts = gtfsTime.split(":");
        if (parts.length != 3) {
            return Optional.empty();
        }

        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);
        int seconds = Integer.parseInt(parts[2]);

        return Optional.of(hours * 3600 + minutes * 60 + seconds);
    }
}
