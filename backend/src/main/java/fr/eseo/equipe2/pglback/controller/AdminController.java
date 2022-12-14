package fr.eseo.equipe2.pglback.controller;

import fr.eseo.equipe2.pglback.dao.GradeRequestDao;
import fr.eseo.equipe2.pglback.enumeration.Role;
import fr.eseo.equipe2.pglback.model.User;
import fr.eseo.equipe2.pglback.payload.request.AddUserRequest;
import fr.eseo.equipe2.pglback.payload.response.Response;
import fr.eseo.equipe2.pglback.service.MailService;
import fr.eseo.equipe2.pglback.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    UserService userService;

    @GetMapping("/users/delete/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer userId) {
        userService.deleteUser(userId);
        return Response.ok().build();
    }

    @PostMapping("/users/add")
    public ResponseEntity<?> addUser(@RequestBody AddUserRequest user) {
//            userDao.save(UserMapper.toUser(userDto));
        return Response.ok().build();
    }

    @GetMapping("/users/upgrade/{userId}/{role}")
    public ResponseEntity<?> upgradeUser(@PathVariable Integer userId, @PathVariable Role role) {
        Optional<User> reqUser = userService.getUser(userId);
        if (reqUser.isEmpty())
            return Response.notFound().build();

        User user = reqUser.get();
        user.setRole(role);

        userService.updateUser(user);
        return Response.ok().build();
    }

    /**
     * Get all grade requests
     * @return List of grade requests
     */
    @GetMapping("/grade-requests")
    public ResponseEntity<?> getGradeRequests() {
       return Response.ok().setPayload(userService.getGradeRequests()).build();
    }

    /**
     * Validate grade request
     */
    @GetMapping("/grade-requests/{id}/validate")
    public ResponseEntity<?> validateGradeRequest(@PathVariable Integer id) {
        if (userService.validateGradeRequest(id))
            return Response.ok().build();
        else
            return Response.exception().build();
    }

    /**
     * Validate grade request
     */
    @GetMapping("/grade-requests/{id}/remove")
    public ResponseEntity<?> removeGradeRequest(@PathVariable Integer id) {
        userService.removeGradeRequest(id);
        return Response.ok().build();
    }
}
