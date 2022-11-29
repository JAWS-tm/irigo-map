package fr.eseo.equipe2.pglback.controller;

import fr.eseo.equipe2.pglback.controller.request.LoginRequest;
import fr.eseo.equipe2.pglback.controller.request.RegisterRequest;
import fr.eseo.equipe2.pglback.controller.request.mapper.UserRequestMapper;
import fr.eseo.equipe2.pglback.dto.response.Response;
import fr.eseo.equipe2.pglback.service.AuthService;
import fr.eseo.equipe2.pglback.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@CrossOrigin
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    AuthService authService;

    /**
     * @author Louise
     * Check if User is authorized
     * @param request of authentication
     * @return Unauthorized or Ok
     */
    @PostMapping("/login")
    public Response login(@RequestBody LoginRequest loginRequest) {
        return Response.ok().setPayload(authService.login(UserRequestMapper.toUserDto(loginRequest)));
    }

    /**
     * Register a new user
     * @param registerRequest request with register data
     * @return Ok or 409 CONFLICT
     */
    @PostMapping("/register")
    public Response register(@RequestBody RegisterRequest registerRequest) {
        return Response.ok().setPayload(authService.register(UserRequestMapper.toUserDto(registerRequest)));
    }

    /**
     * Get current user data
     * @param principal currentUser
     * @return Ok or Unauthorized
     */
    @GetMapping("/me")
    public Response me(Principal principal) {
        if (principal == null)
            return Response.unauthorized();

        return Response.ok().setPayload(authService.me(principal.getName()));
    }
}
