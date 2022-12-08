package fr.eseo.equipe2.pglback.model;


import javax.persistence.*;

//table comment avec notation et commentary dedans pour les notes et les coms

@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;


    @ManyToOne
    private User user;

    @Column(nullable = false)
    private String numberLine;

    @Column
    private Integer notation;

    @Column(length = 516)
    private String commentary;

    public Comment() {
    }

    /**
     * when we want new user we take all parameters
     *
     * @param user       user object
     * @param numberLine (01,02 ... 12)
     * @param notation   (0,1,2,3,4,5)
     * @param commentary (message)
     */
    public Comment(User user, String numberLine, Integer notation, String commentary) {
        this.user = user;
        this.numberLine = numberLine;
        this.notation = notation;
        this.commentary = commentary;
    }


    // Getters & Setters
    public int getId() {
        return id;
    }

    public Comment setId(int id) {
        this.id = id;
        return this;
    }

    public User getUser() {
        return user;
    }
    public Comment setUser(User user) {
        this.user = user;
        return this;
    }

    public String getNumberLine() {
        return numberLine;
    }

    public Comment setNumberLine(String numberLine) {
        this.numberLine = numberLine;
        return this;
    }

    public Integer getNotation() {
        return notation;
    }

    public Comment setNotation(Integer notation) {
        this.notation = notation;
        return this;
    }

    public String getCommentary() {
        return commentary;
    }

    public Comment setCommentary(String commentary) {
        this.commentary = commentary;
        return this;
    }
}
