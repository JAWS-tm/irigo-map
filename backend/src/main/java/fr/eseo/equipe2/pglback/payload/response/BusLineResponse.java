package fr.eseo.equipe2.pglback.payload.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import fr.eseo.equipe2.pglback.model.Coordinate;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import java.util.Date;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class BusLineResponse {
    private String lineId;

    private String lineName;

    private String lineColor;

    private List<List<Coordinate>> coordinates;

    public BusLineResponse() {
    }

    public String getLineId() {
        return lineId;
    }

    public BusLineResponse setLineId(String lineId) {
        this.lineId = lineId;
        return this;
    }

    public String getLineName() {
        return lineName;
    }

    public BusLineResponse setLineName(String lineName) {
        this.lineName = lineName;
        return this;
    }

    public String getLineColor() {
        return lineColor;
    }

    public BusLineResponse setLineColor(String lineColor) {
        this.lineColor = lineColor;
        return this;
    }

    public List<List<Coordinate>> getCoordinates() {
        return coordinates;
    }

    public BusLineResponse setCoordinates(List<List<Coordinate>> coordinates) {
        this.coordinates = coordinates;
        return this;
    }
}
