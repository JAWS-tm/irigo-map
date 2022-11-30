package fr.eseo.equipe2.pglback.dto;

import fr.eseo.equipe2.pglback.model.User;

public class CommentDto {
    private int id;
    private User user;
    private String numberLine;
    private Integer notation;
    private String comment;


    public CommentDto() {
    }


    public int getId() { return id;}
    public CommentDto setId(int id) {
        this.id = id;
        return this;
    }
    public User getUser() { return user;}
    public CommentDto setUser(User user) {
        this.user = user;
        return this;
    }
    public String getNumberLine() {
        return numberLine;
    }

    public CommentDto setNumberLine(String numberLine) {
        this.numberLine = numberLine;
        return this;
    }

    public Integer getNotation() {
        return notation;
    }

    public CommentDto setNotation(Integer notation) {
        this.notation = notation;
        return this;
    }

    public String getCommentary() {return comment;}

    public CommentDto setCommentary(String comment) {
        this.comment = comment;
        return this;
    }
}
