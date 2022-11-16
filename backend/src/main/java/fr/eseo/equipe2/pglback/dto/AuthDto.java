package fr.eseo.equipe2.pglback.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AuthDto {
    private String email;
    private String accessToken;
}
