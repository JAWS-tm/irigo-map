package fr.eseo.equipe2.pglback.payload;

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
    private UserDto user;
    private String accessToken;

    public AuthDto() {
    }

    public AuthDto(UserDto user, String accessToken) {
        this.user = user;
        this.accessToken = accessToken;
    }

    public UserDto getUser() {
        return user;
    }

    public AuthDto setUser(UserDto user) {
        this.user = user;
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
