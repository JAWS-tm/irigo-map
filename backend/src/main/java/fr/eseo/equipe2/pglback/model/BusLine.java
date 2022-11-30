package fr.eseo.equipe2.pglback.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "bus_lines")
public class BusLine {
    @Id
    private String lineId;

    @Column(nullable = false)
    private String lineName;

    @Column(nullable = false)
    private String lineColor;

//    @ElementCollection
//    @CollectionTable(name="line_route_coordinates")
//    private List<Coordinate> coordinates;

    @OneToMany(mappedBy = "busLine", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @OrderBy("orderKey asc")
    private List<LinePoint> linePoints;

    public String getLineId() {
        return lineId;
    }

    public BusLine setLineId(String lineId) {
        this.lineId = lineId;
        return this;
    }

    public String getLineName() {
        return lineName;
    }

    public BusLine setLineName(String lineName) {
        this.lineName = lineName;
        return this;
    }

    public String getLineColor() {
        return lineColor;
    }

    public BusLine setLineColor(String lineColor) {
        this.lineColor = lineColor;
        return this;
    }

    public List<LinePoint> getLinePoints() {
        return linePoints;
    }

    public BusLine setLinePoints(List<LinePoint> linePoints) {
        this.linePoints = linePoints;
        return this;
    }
}
