package fr.eseo.equipe2.pglback.dao;

import fr.eseo.equipe2.pglback.model.GradeRequest;
import fr.eseo.equipe2.pglback.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GradeRequestDao extends JpaRepository<GradeRequest, Integer> {
    Optional<GradeRequest> findByUser(User user);
}
