package fr.eseo.equipe2.pglback.controller;

import fr.eseo.equipe2.pglback.dto.UserDto;
import fr.eseo.equipe2.pglback.dto.response.Response;
import fr.eseo.equipe2.pglback.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
    @PutMapping("/change/{email}")
    public Response updateUser(@RequestBody UserDto userDto, @PathVariable String email){
        userService.updateUser(userDto);
        System.out.println(userDto);
        return Response.ok();
    }

    /**
     * @author Louise
     * if we want delete user with his unique id
     * @param email email of the user
     */
    @DeleteMapping("/user/{email}")
    public Response deleteUser(@PathVariable String email){
        userService.deleteUser(email);
        return Response.ok();
    }


}
