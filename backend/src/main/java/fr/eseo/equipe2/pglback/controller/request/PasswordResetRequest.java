package fr.eseo.equipe2.pglback.controller.request;

public class PasswordResetRequest {
    private String token;

    private String password;

    public String getToken() {
        return token;
    }

    public PasswordResetRequest setToken(String token) {
        this.token = token;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public PasswordResetRequest setPassword(String password) {
        this.password = password;
        return this;
    }
}
