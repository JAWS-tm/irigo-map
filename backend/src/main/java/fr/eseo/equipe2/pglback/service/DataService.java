package fr.eseo.equipe2.pglback.service;

import fr.eseo.equipe2.pglback.controller.request.SaveRequest;
import fr.eseo.equipe2.pglback.dao.UserDao;
import fr.eseo.equipe2.pglback.dto.UserDto;
import fr.eseo.equipe2.pglback.dto.mapper.UserMapper;
import fr.eseo.equipe2.pglback.exception.EntityType;
import fr.eseo.equipe2.pglback.exception.ExceptionType;
import fr.eseo.equipe2.pglback.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import fr.eseo.equipe2.pglback.exception.EntityType;
import fr.eseo.equipe2.pglback.exception.ExceptionType;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static fr.eseo.equipe2.pglback.dto.response.Response.exception;

@Service
public class DataService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private PasswordEncoder passwordEncoder;


    /**
     * Update user data
     * @param saveRequest user object
     */
    public void updateUser(SaveRequest saveRequest) {
        User user= new User();
        user.setFirstName(saveRequest.getFirstName());
        user.setLastName(saveRequest.getLastName());
        user.setPassword(passwordEncoder.encode(saveRequest.getPassword()));
        user.setSex(saveRequest.getSex());
        userDao.save(user);
    }


    /**
     * Remove user by email
     * @param email user email
     */
    public void deleteUser(String email) {

        if(userDao.existsByEmail(email)){
            System.out.println("exist");
            userDao.deleteByEmail(email);
        }
    }
}
