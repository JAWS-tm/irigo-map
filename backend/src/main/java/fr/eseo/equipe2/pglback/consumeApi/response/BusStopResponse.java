package fr.eseo.equipe2.pglback.consumeApi.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import fr.eseo.equipe2.pglback.model.Coordinate;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BusStopResponse {
    private String arret_id;
    private Integer arret_code;
    private String arret_nom;
    private Coordinate arret_coordonnees;
    private Integer arret_accessibilite;


    // Getters & Setters
    public String getArret_id() {
        return arret_id;
    }

    public BusStopResponse setArret_id(String arret_id) {
        this.arret_id = arret_id;
        return this;
    }

    public Integer getArret_code() {
        return arret_code;
    }

    public BusStopResponse setArret_code(Integer arret_code) {
        this.arret_code = arret_code;
        return this;
    }

    public String getArret_nom() {
        return arret_nom;
    }

    public BusStopResponse setArret_nom(String arret_nom) {
        this.arret_nom = arret_nom;
        return this;
    }

    public Coordinate getArret_coordonnees() {
        return arret_coordonnees;
    }

    public BusStopResponse setArret_coordonnees(Coordinate arret_coordonnees) {
        this.arret_coordonnees = arret_coordonnees;
        return this;
    }

    public Integer getArret_accessibilite() {
        return arret_accessibilite;
    }

    public BusStopResponse setArret_accessibilite(Integer arret_accessibilite) {
        this.arret_accessibilite = arret_accessibilite;
        return this;
    }
}
