package fr.eseo.equipe2.pglback.controller;

import fr.eseo.equipe2.pglback.controller.request.RegisterRequest;
import fr.eseo.equipe2.pglback.controller.request.mapper.UserRequestMapper;
import fr.eseo.equipe2.pglback.dto.UserDto;
import fr.eseo.equipe2.pglback.dto.response.Response;
import fr.eseo.equipe2.pglback.model.User;
import fr.eseo.equipe2.pglback.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/{id}")
    public Response getUser(@PathVariable int id) {
        return Response.ok().setPayload(userService.findById(id));
    }

    /**
     * when we do modification on user
     * @param userDto
     */
    @PutMapping("/user")
    public Response updateUser(@RequestBody UserDto userDto){
        userService.updateUser(userDto);
        return Response.ok();
    }

    /**
     * if we want delete user with his unique id
     * @param email email of the user
     */

    @DeleteMapping("/user/{email}")
    public Response deleteUser(@PathVariable String email){
        userService.deleteUser(email);
        return Response.ok();
    }
}
