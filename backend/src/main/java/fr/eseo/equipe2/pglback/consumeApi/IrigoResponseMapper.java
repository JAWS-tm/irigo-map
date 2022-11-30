package fr.eseo.equipe2.pglback.consumeApi;

import fr.eseo.equipe2.pglback.consumeApi.response.BusLineResponse;
import fr.eseo.equipe2.pglback.consumeApi.response.BusResponse;
import fr.eseo.equipe2.pglback.consumeApi.response.BusStopResponse;
import fr.eseo.equipe2.pglback.model.*;

import java.util.ArrayList;
import java.util.List;

public class IrigoResponseMapper {
    public static Bus toBusEntity(BusResponse busRes) {
        return new Bus().setDelay(busRes.getEcart())
                .setDestination(busRes.getDest())
                .setCoordinates(busRes.getCoordonnees())
                .setLineName(busRes.getNomligne())
                .setLineNb(busRes.getMnemoligne())
                .setNextStopName(busRes.getNomarret())
                .setNextStopTime(busRes.getHarret())
                .setVehicleId(busRes.getNovh());
    }

    public static BusStop toBusStopEntity(BusStopResponse busStopRes) {
        return new BusStop().setId(busStopRes.getStop_id())
                .setName(busStopRes.getStop_name())
                .setCoordinates(busStopRes.getStop_coordinates())
                .setWheelchairBoarding(busStopRes.getWheelchair_boarding() == 1);
    }

    public static BusLine toBusLineEntity(BusLineResponse busLineRes) {
        BusLine busLine = new BusLine().setLineId(busLineRes.getRoute_id())
                .setLineColor(busLineRes.getRoute_color())
                .setLineName(busLineRes.getRoute_long_name());

        List<LinePoint> linePoints = new ArrayList<>();
        Integer orderId = 0;
        Integer groupId = 0;
        for (List<List<Double>> routes: busLineRes.getShape().getGeometry().getCoordinates()) {
            for (List<Double> routePoints: routes) {
                Coordinate coord = new Coordinate(routePoints.get(1), routePoints.get(0));
                linePoints.add(new LinePoint(orderId, groupId, coord, busLine));
                orderId++;
            }
            groupId++;
        }

        busLine.setLinePoints(linePoints);

        return busLine;
    }
}
