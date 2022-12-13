package fr.eseo.equipe2.pglback.controller;

import fr.eseo.equipe2.pglback.payload.UserDto;
import fr.eseo.equipe2.pglback.payload.request.ForgotPasswordRequest;
import fr.eseo.equipe2.pglback.payload.request.PasswordResetRequest;
import fr.eseo.equipe2.pglback.payload.response.Response;
import fr.eseo.equipe2.pglback.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public Response getUsers() {
        return Response.ok().setPayload(userService.getUsers());
    }

    @GetMapping("/{email}")
    public Response getUser(@PathVariable String email) {
        return Response.ok().setPayload(userService.findByEmail(email));
    }

    /**
     * @author Louise
     * when we do modification on user
     * @param userDto
     */
    @PutMapping("/{email}")
    public Response<?> updateUser(@RequestBody UserDto userDto, @PathVariable String email){
        userService.updateUser(userDto);
        System.out.println(userDto);
        return Response.ok();
    }

    /**
     * @author Louise
     * if we want delete user with his unique id
     * @param email email of the user
     */

    @DeleteMapping("/{email}")
    public Response<?> deleteUser(@PathVariable String email){
        userService.deleteUser(email);
        return Response.ok();
    }

    /**
     * Forgot password (create & send recovery link)
     * @param forgotPasswordRequest account email to recover
     */
    @PostMapping("/forgot-password")
    public Response<?> forgotPassword(@RequestBody ForgotPasswordRequest forgotPasswordRequest) {
        userService.forgotPassword(forgotPasswordRequest.getEmail());
        return Response.ok();
    }

    /**
     * Validate the password token
     * @param token token to verify
     */
    @GetMapping("/validate-password-token/{token}")
    public ResponseEntity<?> isTokenValid(@PathVariable String token) {
        String result = userService.validatePasswordToken(token);

        if (result != null && result.equals("invalidToken"))
            return Response.badRequest().addErrorMsgToResponse("Le lien de réinitialisation est invalide.").build();
        else if (result != null && result.equals("expired"))
            return Response.badRequest().addErrorMsgToResponse("Le lien est expiré, veuillez refaire une demande de réinitialisation.").build();

        return Response.ok().build();
    }


    /**
     * Update the password
     * @param passwordResetRequest params used to update (token & password)
     */
    @PostMapping("/reset-password")
    public ResponseEntity<?> savePassword(@RequestBody PasswordResetRequest passwordResetRequest) {
        String result = userService.validatePasswordToken(passwordResetRequest.getToken());

        if (result != null && result.equals("invalidToken"))
            return Response.badRequest().addErrorMsgToResponse("Le lien de réinitialisation est invalide.").build();
        else if (result != null && result.equals("expired"))
            return Response.badRequest().addErrorMsgToResponse("Le lien est expiré, veuillez refaire une demande de réinitialisation.").build();


        userService.updatePassword(passwordResetRequest.getToken(), passwordResetRequest.getPassword());
        return Response.ok().build();
    }
}
