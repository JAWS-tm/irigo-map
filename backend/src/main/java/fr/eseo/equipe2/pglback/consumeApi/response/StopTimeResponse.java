package fr.eseo.equipe2.pglback.consumeApi.response;

import java.util.Date;

public class StopTimeResponse {
    private String mnemoarret;
    private Date arriveetheorique;
    private Date departtheorique;
    private Date arrivee;
    private Date depart;
    private String fiable;
    private String mnemoligne;
    private String dest;
    private String codeparcours;

    public String getMnemoarret() {
        return mnemoarret;
    }

    public StopTimeResponse setMnemoarret(String mnemoarret) {
        this.mnemoarret = mnemoarret;
        return this;
    }

    public Date getArriveetheorique() {
        return arriveetheorique;
    }

    public StopTimeResponse setArriveetheorique(Date arriveetheorique) {
        this.arriveetheorique = arriveetheorique;
        return this;
    }

    public Date getDeparttheorique() {
        return departtheorique;
    }

    public StopTimeResponse setDeparttheorique(Date departtheorique) {
        this.departtheorique = departtheorique;
        return this;
    }

    public Date getArrivee() {
        return arrivee;
    }

    public StopTimeResponse setArrivee(Date arrivee) {
        this.arrivee = arrivee;
        return this;
    }

    public Date getDepart() {
        return depart;
    }

    public StopTimeResponse setDepart(Date depart) {
        this.depart = depart;
        return this;
    }

    public String getFiable() {
        return fiable;
    }

    public StopTimeResponse setFiable(String fiable) {
        this.fiable = fiable;
        return this;
    }

    public String getMnemoligne() {
        return mnemoligne;
    }

    public StopTimeResponse setMnemoligne(String mnemoligne) {
        this.mnemoligne = mnemoligne;
        return this;
    }

    public String getDest() {
        return dest;
    }

    public StopTimeResponse setDest(String dest) {
        this.dest = dest;
        return this;
    }

    public String getCodeparcours() {
        return codeparcours;
    }

    public StopTimeResponse setCodeparcours(String codeparcours) {
        this.codeparcours = codeparcours;
        return this;
    }
}
