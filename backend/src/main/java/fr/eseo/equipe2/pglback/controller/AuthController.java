package fr.eseo.equipe2.pglback.controller;

import fr.eseo.equipe2.pglback.payload.request.LoginRequest;
import fr.eseo.equipe2.pglback.payload.request.RegisterRequest;
import fr.eseo.equipe2.pglback.payload.request.mapper.UserRequestMapper;
import fr.eseo.equipe2.pglback.payload.AuthDto;
import fr.eseo.equipe2.pglback.payload.UserDto;
import fr.eseo.equipe2.pglback.payload.response.Response;
import fr.eseo.equipe2.pglback.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@CrossOrigin
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    AuthService authService;

    @PostMapping("/login")
    /**
     * @author Louise
     * @version 1.1
     * Check if User is authorized
     * @param request of authentication
     * @return Unauthorized or Ok
     */
    public Response<AuthDto> login(@RequestBody LoginRequest loginRequest) {
        return Response.<AuthDto>ok().setPayload(authService.login(UserRequestMapper.toUserDto(loginRequest)));
    }

    /**
     * Register a new user
     * @param registerRequest request with register data
     * @return Ok or 409 CONFLICT
     */
    @PostMapping("/register")
    public Response<UserDto> register(@RequestBody RegisterRequest registerRequest) {
        return Response.<UserDto>ok().setPayload(authService.register(UserRequestMapper.toUserDto(registerRequest)));
    }

    /**
     * Get current user data
     * @param principal currentUser
     * @return Ok or Unauthorized
     */
    @GetMapping("/me")
    public Response<UserDto> me(Principal principal) {
        if (principal == null)
            return Response.unauthorized();

        return Response.<UserDto>ok().setPayload(authService.me(principal.getName()));
    }
}
