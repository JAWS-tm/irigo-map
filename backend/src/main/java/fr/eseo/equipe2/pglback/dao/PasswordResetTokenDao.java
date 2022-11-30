package fr.eseo.equipe2.pglback.dao;

import fr.eseo.equipe2.pglback.model.PasswordResetToken;
import fr.eseo.equipe2.pglback.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface PasswordResetTokenDao extends JpaRepository<PasswordResetToken, Integer> {
    Optional<PasswordResetToken> findByToken(String token);

    PasswordResetToken getByToken(String token);

    @Transactional
    void deleteAllByUser(User user);

}
