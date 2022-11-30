package fr.eseo.equipe2.pglback.dao;

import fr.eseo.equipe2.pglback.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface UserDao extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
    User getByEmail(String email);
    Boolean existsByEmail(String email);
    @Transactional
    void deleteByEmail(String email);
}
