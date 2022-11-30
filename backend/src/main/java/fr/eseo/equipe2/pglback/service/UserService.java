package fr.eseo.equipe2.pglback.service;

import fr.eseo.equipe2.pglback.dao.PasswordResetTokenDao;
import fr.eseo.equipe2.pglback.dao.UserDao;
import fr.eseo.equipe2.pglback.dto.UserDto;
import fr.eseo.equipe2.pglback.dto.mapper.UserMapper;
import fr.eseo.equipe2.pglback.exception.CustomException;
import fr.eseo.equipe2.pglback.exception.EntityType;
import fr.eseo.equipe2.pglback.exception.ExceptionType;
import fr.eseo.equipe2.pglback.model.PasswordResetToken;
import fr.eseo.equipe2.pglback.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordResetTokenDao passwordResetTokenDao;

    @Autowired
    private MailService mailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

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
     * Find a user by email
     * @param userEmail user
     * @return corresponding user
     */
    public User findByEmail(String userEmail) {
        Optional<User> user = userDao.findByEmail(userEmail);
        if (!user.isPresent()) {
            throw exception(EntityType.USER, ExceptionType.ENTITY_NOT_FOUND, String.valueOf(userEmail));
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
     * Send mail with reset link to the given email
     * @param email user email
     */
    public void forgotPassword(String email) {
        Optional<User> userReq = userDao.findByEmail(email);
        if (userReq.isEmpty())
            throw exception(EntityType.USER, ExceptionType.ENTITY_NOT_FOUND);

        User user = userReq.get();

        String token = UUID.randomUUID().toString();

        PasswordResetToken myToken = new PasswordResetToken(token, user);
        passwordResetTokenDao.save(myToken);

        HashMap<String, Object> mailData = new HashMap<>();
        mailData.put("name", user.getFirstName());
        mailData.put("token", token);

        mailService.sendHtmlMessage(user.getEmail(), "Réinitialiser votre mot de passe","resetPassword.html", mailData);
    }

    /**
     * Check if password token is valid
     * @param token token to validate
     * @return invalidToken if token is not valid, expired if token is expired or null if token is valid
     */
    public String validatePasswordToken(String token) {
        Optional<PasswordResetToken> passToken = passwordResetTokenDao.findByToken(token);

        return passToken.isEmpty() ? "invalidToken"
                : isTokenExpired(passToken.get()) ? "expired"
                : null;
    }

    /**
     * Verify the validity of the given token
     * @param token token to validate
     * @return if its expired or not
     */
    private Boolean isTokenExpired(PasswordResetToken token) {
        Calendar cal = Calendar.getInstance();
        return token.getExpiryDate().before(cal.getTime());
    }

    /**
     * update the password user by the token
     * @param password new password
     * @param token validation token
     */
    public void updatePassword(String token, String password) {
        User user = passwordResetTokenDao.getByToken(token).getUser();


        user.setPassword(passwordEncoder.encode(password));
        userDao.save(user);

        // Remove all generated password recovery codes
        passwordResetTokenDao.deleteAllByUser(user);

        // notify the user
        mailService.sendMessage(user.getEmail(), "Mot de passe réinitialisé", "Bonjour "+ user.getFirstName() +", \nVotre mot de passe vient d'être réinitialisé avec succès. \nSi vous n'etes pas à l'origine de ce changement contactez un administrateur dès que possible.");
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
