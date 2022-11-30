package fr.eseo.equipe2.pglback.controller.request;

import org.springframework.lang.NonNull;

public class ForgotPasswordRequest {
    @NonNull
    private String email;

    @NonNull
    public String getEmail() {
        return email;
    }

    public ForgotPasswordRequest setEmail(@NonNull String email) {
        this.email = email;
        return this;
    }
}
