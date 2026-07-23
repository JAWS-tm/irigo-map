package fr.eseo.equipe2.pglback.consumeApi.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import fr.eseo.equipe2.pglback.model.Coordinate;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class BusStopResponse {
    @JsonProperty("arret_id")
    private String id;

    @JsonProperty("arret_code")
    private Integer code;

    @JsonProperty("arret_nom")
    private String name;

    @JsonProperty("arret_coordonnees")
    private Coordinate coordinates;

    @JsonProperty("arret_accessibilite")
    private Integer accessibility;
}
