package fr.eseo.equipe2.pglback.payload.request;

import javax.persistence.Column;

public class DataScientistGradeRequest {
    private String job;

    private String company;

    private String description;

    public String getJob() {
        return job;
    }

    public DataScientistGradeRequest setJob(String job) {
        this.job = job;
        return this;
    }

    public String getCompany() {
        return company;
    }

    public DataScientistGradeRequest setCompany(String company) {
        this.company = company;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public DataScientistGradeRequest setDescription(String description) {
        this.description = description;
        return this;
    }
}
