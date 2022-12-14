package fr.eseo.equipe2.pglback.dao;

import fr.eseo.equipe2.pglback.model.Comment;
import fr.eseo.equipe2.pglback.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentDao extends JpaRepository<Comment, Integer> {

    List<Comment> findCommentsByUser(User user);

    List<Comment> getAllByNumberLine(String numberLine);
    Boolean existsByUserAndNumberLine(User user, String numberLine);
    Comment getByUserAndNumberLine(User user, String numberLine);

}
