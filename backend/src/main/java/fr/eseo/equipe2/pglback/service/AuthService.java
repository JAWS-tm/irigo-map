package fr.eseo.equipe2.pglback.service;

import fr.eseo.equipe2.pglback.dao.UserDao;
import fr.eseo.equipe2.pglback.payload.AuthDto;
import fr.eseo.equipe2.pglback.payload.UserDto;
import fr.eseo.equipe2.pglback.payload.mapper.UserMapper;
import fr.eseo.equipe2.pglback.exception.CustomException;
import fr.eseo.equipe2.pglback.exception.EntityType;
import fr.eseo.equipe2.pglback.exception.ExceptionType;
import fr.eseo.equipe2.pglback.model.User;
import fr.eseo.equipe2.pglback.security.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UserDao userDao;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthDto login(UserDto userDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userDto.getEmail(), userDto.getPassword())
            );
            User user = (User) authentication.getPrincipal();
            String accessToken = jwtUtil.generateAccessToken(user);

            return new AuthDto(user.getEmail(), accessToken);
        } catch (BadCredentialsException ex) {
            System.out.println(ex);
            throw exception(EntityType.USER, ExceptionType.BAD_CREDENTIALS, "Bad credentials");
        }
    }

    /**
     * Register a new user
     * @param userDto register form data
     * @return created user
     * @throws CustomException.DuplicateEntityException if user already exist
     */
    public UserDto register(UserDto userDto) {
        if (userDao.existsByEmail(userDto.getEmail())) {
            throw exception(EntityType.USER, ExceptionType.DUPLICATE_ENTITY, userDto.getEmail());
        }

        User user = UserMapper.toUser(userDto)
                            .setPassword(passwordEncoder.encode(userDto.getPassword()));

        return UserMapper.toUserDto(userDao.save(user).setPassword(""));
    }

    /**
     * Get current user data
     * @param email email of logged user
     * @return user data
     */
    public UserDto me(String email) {
        if (!userDao.existsByEmail(email)) {
            throw exception(EntityType.USER, ExceptionType.ENTITY_NOT_FOUND, email);
        }

        User user = userDao.getByEmail(email);

        return UserMapper.toUserDto(user.setPassword(""));
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
