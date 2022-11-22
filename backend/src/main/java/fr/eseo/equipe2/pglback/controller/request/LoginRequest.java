package fr.eseo.equipe2.pglback.controller.request;

//import lombok.Getter;
//import lombok.Setter;
//import lombok.experimental.Accessors;

//@Getter
//@Setter
//@Accessors(chain = true)

/**
 * Request used for login
 */
public class LoginRequest {
    private String email;
    private String password;

    public LoginRequest() {
    }

    public String getEmail() {
        return email;
    }

    public LoginRequest setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public LoginRequest setPassword(String password) {
        this.password = password;
        return this;
    }
}