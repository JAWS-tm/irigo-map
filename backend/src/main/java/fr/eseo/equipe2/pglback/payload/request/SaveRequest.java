package fr.eseo.equipe2.pglback.payload.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.sun.istack.NotNull;
import fr.eseo.equipe2.pglback.enumeration.UserSex;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SaveRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private UserSex sex;

    public SaveRequest(){

    }
    public String getFirstName() {
        return firstName;
    }

    public SaveRequest setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public SaveRequest setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public SaveRequest setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public SaveRequest setPassword(String password) {
        this.password = password;
        return this;
    }

    public UserSex getSex() {
        return sex;
    }

    public SaveRequest setSex(UserSex sex) {
        this.sex = sex;
        return this;
    }
}

