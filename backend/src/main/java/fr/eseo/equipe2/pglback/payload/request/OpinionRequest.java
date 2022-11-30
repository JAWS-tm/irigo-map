package fr.eseo.equipe2.pglback.payload.request;

public class OpinionRequest {
    private Integer notation;
    private String commentary;

    private String numberLine;

    public OpinionRequest() {
    }

    public Integer getNotation() {
        return notation;
    }

    public OpinionRequest setNotation(Integer notation) {
        this.notation = notation;
        return this;
    }

    public String getCommentary() {
        return commentary;
    }

    public OpinionRequest setCommentary(String commentary) {
        this.commentary = commentary;
        return this;
    }

    public String getNumberLine() {
        return numberLine;
    }

    public void setNumberLine(String numberLine) {
        this.numberLine = numberLine;
    }
}
