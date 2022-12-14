package fr.eseo.equipe2.pglback.model;

import javax.persistence.*;

@Entity
@Table(name = "grade_requests")
public class GradeRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JoinColumn(nullable = false, name = "user_id", unique = true)
    private User user;

    @Column(nullable = false, length = 30)
    private String job;

    @Column(nullable = false, length = 30)
    private String company;

    @Column(nullable = false, length = 500)
    private String description;

    public int getId() {
        return id;
    }

    public GradeRequest setId(int id) {
        this.id = id;
        return this;
    }

    public User getUser() {
        return user;
    }

    public GradeRequest setUser(User user) {
        this.user = user;
        return this;
    }

    public String getJob() {
        return job;
    }

    public GradeRequest setJob(String job) {
        this.job = job;
        return this;
    }

    public String getCompany() {
        return company;
    }

    public GradeRequest setCompany(String company) {
        this.company = company;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public GradeRequest setDescription(String description) {
        this.description = description;
        return this;
    }
}
