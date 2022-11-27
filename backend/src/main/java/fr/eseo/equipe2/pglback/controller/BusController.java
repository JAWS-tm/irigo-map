package fr.eseo.equipe2.pglback.controller;

import fr.eseo.equipe2.pglback.payload.response.*;
import fr.eseo.equipe2.pglback.service.BusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("bus")
public class BusController {

    @Autowired
    private BusService busService;

    @GetMapping
    public Response<List<BusResponse>> getBuses() {
        return Response.<List<BusResponse>>ok().setPayload(busService.getBuses());
    }

    @GetMapping("/stops")
    public Response<List<BusStopResponse>> getBusStops() {
        return Response.<List<BusStopResponse>>ok().setPayload(busService.getStops());
    }

    @GetMapping("/stops/{id}/timetable")
    public Response<StopTimetableResponse> getStopTimetable(@PathVariable String id) {
        return Response.<StopTimetableResponse>ok().setPayload(busService.getStopTimetable(id));
    }

    @GetMapping("/lines")
    public Response<List<BusLineResponse>> getBusLines() {
        return Response.<List<BusLineResponse>>ok().setPayload(busService.getLines());
    }


}
