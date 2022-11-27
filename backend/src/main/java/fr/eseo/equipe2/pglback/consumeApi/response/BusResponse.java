package fr.eseo.equipe2.pglback.consumeApi.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import fr.eseo.equipe2.pglback.model.Coordinate;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BusResponse {
    private Integer idvh;
    private Integer novh;
    private Integer idligne;
    private Integer iddesserte;
    private Integer idarret;
    private Date harret;
    private Date ts_maj;
    private Integer x;
    private Integer y;
    private Integer cap;
    private Integer ecart;
    private Integer numarret;
    private Integer idparcours;
    private Coordinate coordonnees;
    private String etat;
    private String type;
    private String sv;
    private String mnemoligne;
    private String nomligne;
    private String mnemoarret;
    private String nomarret;
    private String dest;

    // Getters & Setters

    public Integer getIdvh() {
        return idvh;
    }

    public BusResponse setIdvh(Integer idvh) {
        this.idvh = idvh;
        return this;
    }

    public Integer getNovh() {
        return novh;
    }

    public BusResponse setNovh(Integer novh) {
        this.novh = novh;
        return this;
    }

    public Integer getIdligne() {
        return idligne;
    }

    public BusResponse setIdligne(Integer idligne) {
        this.idligne = idligne;
        return this;
    }

    public Integer getIddesserte() {
        return iddesserte;
    }

    public BusResponse setIddesserte(Integer iddesserte) {
        this.iddesserte = iddesserte;
        return this;
    }

    public Integer getIdarret() {
        return idarret;
    }

    public BusResponse setIdarret(Integer idarret) {
        this.idarret = idarret;
        return this;
    }

    public Date getHarret() {
        return harret;
    }

    public BusResponse setHarret(Date harret) {
        this.harret = harret;
        return this;
    }

    public Date getTs_maj() {
        return ts_maj;
    }

    public BusResponse setTs_maj(Date ts_maj) {
        this.ts_maj = ts_maj;
        return this;
    }

    public Integer getX() {
        return x;
    }

    public BusResponse setX(Integer x) {
        this.x = x;
        return this;
    }

    public Integer getY() {
        return y;
    }

    public BusResponse setY(Integer y) {
        this.y = y;
        return this;
    }

    public Integer getCap() {
        return cap;
    }

    public BusResponse setCap(Integer cap) {
        this.cap = cap;
        return this;
    }

    public Integer getEcart() {
        return ecart;
    }

    public BusResponse setEcart(Integer ecart) {
        this.ecart = ecart;
        return this;
    }

    public Integer getNumarret() {
        return numarret;
    }

    public BusResponse setNumarret(Integer numarret) {
        this.numarret = numarret;
        return this;
    }

    public Integer getIdparcours() {
        return idparcours;
    }

    public BusResponse setIdparcours(Integer idparcours) {
        this.idparcours = idparcours;
        return this;
    }

    public Coordinate getCoordonnees() {
        return coordonnees;
    }

    public BusResponse setCoordonnees(Coordinate coordonnees) {
        this.coordonnees = coordonnees;
        return this;
    }

    public String getEtat() {
        return etat;
    }

    public BusResponse setEtat(String etat) {
        this.etat = etat;
        return this;
    }

    public String getType() {
        return type;
    }

    public BusResponse setType(String type) {
        this.type = type;
        return this;
    }

    public String getSv() {
        return sv;
    }

    public BusResponse setSv(String sv) {
        this.sv = sv;
        return this;
    }

    public String getMnemoligne() {
        return mnemoligne;
    }

    public BusResponse setMnemoligne(String mnemoligne) {
        this.mnemoligne = mnemoligne;
        return this;
    }

    public String getNomligne() {
        return nomligne;
    }

    public BusResponse setNomligne(String nomligne) {
        this.nomligne = nomligne;
        return this;
    }

    public String getMnemoarret() {
        return mnemoarret;
    }

    public BusResponse setMnemoarret(String mnemoarret) {
        this.mnemoarret = mnemoarret;
        return this;
    }

    public String getNomarret() {
        return nomarret;
    }

    public BusResponse setNomarret(String nomarret) {
        this.nomarret = nomarret;
        return this;
    }

    public String getDest() {
        return dest;
    }

    public BusResponse setDest(String dest) {
        this.dest = dest;
        return this;
    }
}
