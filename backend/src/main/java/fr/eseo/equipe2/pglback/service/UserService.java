package fr.eseo.equipe2.pglback.service;

import fr.eseo.equipe2.pglback.dao.UserDao;
import fr.eseo.equipe2.pglback.payload.UserDto;
import fr.eseo.equipe2.pglback.payload.mapper.UserMapper;
import fr.eseo.equipe2.pglback.exception.CustomException;
import fr.eseo.equipe2.pglback.exception.EntityType;
import fr.eseo.equipe2.pglback.exception.ExceptionType;
import fr.eseo.equipe2.pglback.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    /**
     * List all users
     * @return list of all the users
     */
    public List<User> getUsers() {
         return userDao.findAll();
    }

    /**
     * Find a user by id
     * @param userId user
     * @return corresponding user
     */
    public User findById(int userId) {
        Optional<User> user = userDao.findById(userId);
        if (!user.isPresent()) {
            throw exception(EntityType.USER, ExceptionType.ENTITY_NOT_FOUND, String.valueOf(userId));
        }
        return user.get();
    }

    /**
     * Update user data
     * @param userDto user object
     */
    public void updateUser(UserDto userDto) {
        userDao.save(UserMapper.toUser(userDto));
    }


    /**
     * Remove user by email
     * @param email user email
     */
    public void deleteUser(String email) {
        if(userDao.existsByEmail(email)){
            userDao.deleteByEmail(email);
        }
    }

    /**
     * Returns a new RuntimeException
     * @param entityType
     * @param exceptionType
     * @param args
     * @return
     */
    private RuntimeException exception(EntityType entityType, ExceptionType exceptionType, String... args) {
        return CustomException.throwException(entityType, exceptionType, args);
    }
}
