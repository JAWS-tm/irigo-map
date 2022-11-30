package fr.eseo.equipe2.pglback.dto;

//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.Setter;

//@Getter
//@Setter
//@AllArgsConstructor

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

/*
 * Data Transfer Object used for transfer data between services & controllers
 */

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthDto {
    private String email;
    private String accessToken;

    public AuthDto() {
    }

    public AuthDto(String email, String accessToken) {
        this.email = email;
        this.accessToken = accessToken;
    }

    public String getEmail() {
        return email;
    }

    public AuthDto setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public AuthDto setAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }
}
