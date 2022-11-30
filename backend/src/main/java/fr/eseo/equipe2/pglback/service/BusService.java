package fr.eseo.equipe2.pglback.service;

import fr.eseo.equipe2.pglback.consumeApi.IrigoApi;
import fr.eseo.equipe2.pglback.consumeApi.response.StopTimeResponse;
import fr.eseo.equipe2.pglback.dao.BusDao;
import fr.eseo.equipe2.pglback.dao.BusLineDao;
import fr.eseo.equipe2.pglback.dao.BusStopDao;
import fr.eseo.equipe2.pglback.exception.CustomException;
import fr.eseo.equipe2.pglback.exception.EntityType;
import fr.eseo.equipe2.pglback.exception.ExceptionType;
import fr.eseo.equipe2.pglback.payload.response.BusLineResponse;
import fr.eseo.equipe2.pglback.payload.response.BusResponse;
import fr.eseo.equipe2.pglback.payload.response.BusStopResponse;
import fr.eseo.equipe2.pglback.payload.response.StopTimetableResponse;
import fr.eseo.equipe2.pglback.payload.response.mapper.ResponseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BusService {
    @Autowired
    private BusDao busDao;

    @Autowired
    private BusLineDao busLineDao;

    @Autowired
    private BusStopDao busStopDao;

    @Autowired
    private IrigoApi irigoApi;

    public List<BusResponse> getBuses() {
        return busDao.findAll().stream()
                .map(ResponseMapper::toBusResponse)
                .collect(Collectors.toList());
    }

    public List<BusStopResponse> getStops() {
        return busStopDao.findAll().stream()
                .map(ResponseMapper::toBusStopResponse)
                .collect(Collectors.toList());
    }

    public List<BusLineResponse> getLines() {
        return busLineDao.findAll().stream()
                .map(ResponseMapper::toBusLineResponse)
                .collect(Collectors.toList());
    }

    public StopTimetableResponse getStopTimetable(String stopId) {
        if (!busStopDao.existsById(stopId))
            throw exception(EntityType.BUS_STOP, ExceptionType.ENTITY_NOT_FOUND);

        Optional<StopTimeResponse[]> stopTimeResponses = irigoApi.fetchStopTimetable(stopId);

        if (stopTimeResponses.isEmpty())
            throw exception(EntityType.BUS_STOP, ExceptionType.NO_VALUE);

        return ResponseMapper.toStopTimetableResponse(stopTimeResponses.get());
    }

    /**
     * Returns a new RuntimeException
     * @param entityType
     * @param exceptionType
     * @param args
     * @return
     */
    private RuntimeException exception(EntityType entityType, ExceptionType exceptionType, String... args) {
        return CustomException.throwException(entityType, exceptionType, args);
    }
}
