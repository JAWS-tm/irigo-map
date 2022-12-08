package fr.eseo.equipe2.pglback.consumeApi.response;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BusLineResponse {
    private String route_id;
    private String route_long_name;
    private String route_color;
    private Shape shape;

    public static class Shape {
        private String type;
        private Geometry geometry;

        public String getType() {
            return type;
        }

        public Shape setType(String type) {
            this.type = type;
            return this;
        }

        public Geometry getGeometry() {
            return geometry;
        }

        public Shape setGeometry(Geometry geometry) {
            this.geometry = geometry;
            return this;
        }

        public static class Geometry {
            private List<List<List<Double>>> coordinates;

            public List<List<List<Double>>> getCoordinates() {
                return coordinates;
            }

            public Geometry setCoordinates(List<List<List<Double>>> coordinates) {
                this.coordinates = coordinates;
                return this;
            }
        }
    }


    public String getRoute_id() {
        return route_id;
    }

    public BusLineResponse setRoute_id(String route_id) {
        this.route_id = route_id;
        return this;
    }

    public String getRoute_long_name() {
        return route_long_name;
    }

    public BusLineResponse setRoute_long_name(String route_long_name) {
        this.route_long_name = route_long_name;
        return this;
    }

    public String getRoute_color() {
        return route_color;
    }

    public BusLineResponse setRoute_color(String route_color) {
        this.route_color = route_color;
        return this;
    }

    public Shape getShape() {
        return shape;
    }

    public BusLineResponse setShape(Shape shape) {
        this.shape = shape;
        return this;
    }
}
