package fr.eseo.equipe2.pglback.service;

import fr.eseo.equipe2.pglback.dao.GradeRequestDao;
import fr.eseo.equipe2.pglback.dao.PasswordResetTokenDao;
import fr.eseo.equipe2.pglback.dao.UserDao;
import fr.eseo.equipe2.pglback.enumeration.Role;
import fr.eseo.equipe2.pglback.model.GradeRequest;
import fr.eseo.equipe2.pglback.payload.UserDto;
import fr.eseo.equipe2.pglback.payload.mapper.UserMapper;
import fr.eseo.equipe2.pglback.exception.CustomException;
import fr.eseo.equipe2.pglback.exception.EntityType;
import fr.eseo.equipe2.pglback.exception.ExceptionType;
import fr.eseo.equipe2.pglback.model.PasswordResetToken;
import fr.eseo.equipe2.pglback.model.User;
import fr.eseo.equipe2.pglback.payload.request.DataScientistGradeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordResetTokenDao passwordResetTokenDao;

    @Autowired
    private GradeRequestDao gradeRequestDao;

    @Autowired
    private MailService mailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * List all users
     * @return list of all the users
     */
    public List<User> getUsers() {
         return userDao.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    /**
     * Get one user
     * @param userId user id
     * @return list of all the users
     */
    public Optional<User> getUser(Integer userId) {
        return userDao.findById(userId);
    }

    /**
     * Find a user by id
     * @param userId user
     * @return corresponding user
     */
    public User findById(int userId) {
        Optional<User> user = userDao.findById(userId);
        if (user.isEmpty()) {
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
        if (user.isEmpty()) {
            throw exception(EntityType.USER, ExceptionType.ENTITY_NOT_FOUND, String.valueOf(userEmail));
        }
        return user.get();
    }

    /**
     * Add user
     * @param userDto user data
     */
    public void addUser(UserDto userDto) {
        if (userDao.existsByEmail(userDto.getEmail()))
            throw exception(EntityType.USER, ExceptionType.DUPLICATE_ENTITY);

        userDao.save(UserMapper.toUser(userDto));
    }


    /**
     * Update user data
     * @param userDto user dto object
     */
    public void updateUser(UserDto userDto) {
        userDao.save(UserMapper.toUser(userDto));
    }

    /**
     * Update user data
     * @param user user object
     */
    public void updateUser(User user) {
        userDao.save(user);
    }


    /**
     * Remove user by email
     * @param email user email
     */
    public void deleteUser(String email) {
        User user = userDao.getByEmail(email);
        mailService.sendMessage(user.getEmail(), "Votre compte à bien été supprimé", "Bonjour "+user.getFirstName()+",\nVotre compte à bien été supprimé. \nÀ bientot !");
        if(userDao.existsByEmail(email))
            userDao.deleteByEmail(email);
    }

    /**
     * Remove user by id
     * @param id user id
     */
    public void deleteUser(Integer id) {
        User user = userDao.getReferenceById(id);
        mailService.sendMessage(user.getEmail(), "Votre compte à été supprimé", "Bonjour "+user.getFirstName()+",\nVotre compte à été supprimé par un administrateur. \nÀ bientot !");

        if(!userDao.existsById(id))
            throw exception(EntityType.USER, ExceptionType.ENTITY_NOT_FOUND);

        userDao.deleteById(id);
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
     * Add a grade request
     * @param request request data
     */
    public boolean requestDataScientistGrade(DataScientistGradeRequest request, String userEmail) {
        if (!userDao.existsByEmail(userEmail)) {
            throw exception(EntityType.USER, ExceptionType.ENTITY_NOT_FOUND, userEmail);
        }

        User user = userDao.getByEmail(userEmail);

        List<Role> unauthorizedRoles = new ArrayList<>(Arrays.asList(Role.ADMIN, Role.DATA_SCIENTIST));

        if (unauthorizedRoles.contains(user.getRole()))
            return false;

        if (gradeRequestDao.findByUser(user).isPresent())
            throw exception(EntityType.GRADE_REQUEST, ExceptionType.DUPLICATE_ENTITY);

        GradeRequest gradeRequest = new GradeRequest();
        gradeRequest.setUser(user)
                .setCompany(request.getCompany())
                .setJob(request.getJob())
                .setDescription(request.getDescription());

        gradeRequestDao.save(gradeRequest);
        return true;
    }

    /**
     * Has done a request
     * @param email user mail
     */
    public boolean hasDoneGradeRequest( String userEmail) {
        if (!userDao.existsByEmail(userEmail)) {
            throw exception(EntityType.USER, ExceptionType.ENTITY_NOT_FOUND, userEmail);
        }

        User user = userDao.getByEmail(userEmail);

        return gradeRequestDao.findByUser(user).isPresent();
    }

    /**
     * Get all grade requests
     */
    public List<GradeRequest> getGradeRequests() {
        return gradeRequestDao.findAll();
    }

    /**
     * Validate grade request
     */
    public boolean validateGradeRequest(Integer id) {
        Optional<GradeRequest> gradeRequest = gradeRequestDao.findById(id);

        if (gradeRequest.isEmpty()) return false;

        User user = gradeRequest.get().getUser();
        user.setRole(Role.DATA_SCIENTIST);

        userDao.save(user);

        gradeRequestDao.deleteById(id);

        mailService.sendMessage(user.getEmail(), "Votre demande de Data Scientist à été acceptée", "Bonjour "+ user.getFirstName()+", \nVotre demande de grade Data Scientist à été étudiée par notre équipe et à été acceptée.\nVous avez désormais accès aux pages d'analyses.");

        return true;
    }

    /**
     * Remove grade request
     */
    public void removeGradeRequest(Integer id) {
        Optional<GradeRequest> gradeRequest = gradeRequestDao.findById(id);

        if (gradeRequest.isEmpty()) return;
        User user = gradeRequest.get().getUser();

        gradeRequestDao.deleteById(id);

        mailService.sendMessage(user.getEmail(), "Votre demande de Data Scientist à été refusée", "Bonjour "+ user.getFirstName()+", \nVotre demande de grade Data Scientist à été étudiée par notre équipe et à été refusé.\nVous ne correspondez pas assez au profil recherché. \nCordialement");
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
