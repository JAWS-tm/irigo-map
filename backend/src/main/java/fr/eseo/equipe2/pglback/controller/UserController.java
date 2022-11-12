package fr.eseo.equipe2.pglback.controller;

import fr.eseo.equipe2.pglback.dao.UserDao;
import fr.eseo.equipe2.pglback.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/auth")

public class UserController {
    @Autowired
    private final UserDao userDao;

    public UserController(UserDao userDao) {
        this.userDao = userDao;
    }

    @GetMapping("/user/{login}")
    public Optional<User> getUser(@PathVariable String login) {
        return userDao.findById(login);
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
     * @param login id of the user
     */
    @DeleteMapping("/user/{login}")
    public void deleteUser(@PathVariable String login){
        if(userDao.existsById(login)){
            userDao.deleteById(login);
        }
    }
}
