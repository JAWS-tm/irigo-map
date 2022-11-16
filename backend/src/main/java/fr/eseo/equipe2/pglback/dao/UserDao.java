package fr.eseo.equipe2.pglback.dao;

import fr.eseo.equipe2.pglback.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDao extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String login);
    Boolean existsByEmail(String email);
    void deleteByEmail(String email);
}
