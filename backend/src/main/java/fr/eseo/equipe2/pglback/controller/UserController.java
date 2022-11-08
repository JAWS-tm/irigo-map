package fr.eseo.equipe2.pglback.controller;

import fr.eseo.clients.model.User;
import fr.eseo.clients.security.AuthRequest;
import fr.eseo.clients.security.AuthResponse;
import fr.eseo.clients.security.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/auth")

public class UserController {
    @Autowired
    private final UserDao userDao;

    public UserController(UserDao userDao) {
        this.userDao = userDao;
    }

    @GetMapping("/user/{id}")
    public Optional<User> getUser(@PathVariable int id) {
        return userDao.findById(id);
    }

    /**
     * when we do modification on user
     * @param user
     */
    @PutMapping("/user")
    public  void updateUser(@RequestBody User user){
        userDao.save(user);
    }

    /**
     * if we want delete user with his unique id
     * @param id
     */
    @DeleteMapping("/user/{id}")
    public void deleteUser(@PathVariable int id){
        if(clientsDao.existsById(id)){
            clientsDao.deleteById(id);
        }
    }
}
