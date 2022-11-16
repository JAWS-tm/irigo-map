package fr.eseo.equipe2.pglback.controller.request;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class LoginRequest {
    private String email;
    private String password;
}