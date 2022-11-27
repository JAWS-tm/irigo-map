package fr.eseo.equipe2.pglback.model;

import javax.persistence.*;

@Entity
@Table(name = "line_route_points")
public class LinePoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "lineId",nullable = false)
    private BusLine busLine;

    @Column(nullable = false)
    private Integer orderKey;

    @Column(nullable = false)
    private Integer routeGroup;

    @Column(nullable = false)
    private Coordinate coordinate;

    public LinePoint() {
    }

    public LinePoint(Integer orderKey, Integer routeGroup, Coordinate coordinate, BusLine busLine) {
        this.orderKey = orderKey;
        this.coordinate = coordinate;
        this.busLine = busLine;
        this.routeGroup = routeGroup;
    }

    public Integer getId() {
        return id;
    }

    public LinePoint setId(Integer id) {
        this.id = id;
        return this;
    }

    public BusLine getBusLine() {
        return busLine;
    }

    public LinePoint setBusLine(BusLine busLine) {
        this.busLine = busLine;
        return this;
    }

    public Integer getOrderKey() {
        return orderKey;
    }

    public LinePoint setOrderKey(Integer order) {
        this.orderKey = order;
        return this;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public LinePoint setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
        return this;
    }

    public Integer getRouteGroup() {
        return routeGroup;
    }

    public LinePoint setRouteGroup(Integer routeGroup) {
        this.routeGroup = routeGroup;
        return this;
    }
}
