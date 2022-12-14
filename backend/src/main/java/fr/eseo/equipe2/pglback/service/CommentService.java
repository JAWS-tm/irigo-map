package fr.eseo.equipe2.pglback.service;

import fr.eseo.equipe2.pglback.dao.CommentDao;
import fr.eseo.equipe2.pglback.dao.UserDao;
import fr.eseo.equipe2.pglback.dto.mapper.CommentMapper;
import fr.eseo.equipe2.pglback.exception.CustomException;
import fr.eseo.equipe2.pglback.exception.EntityType;
import fr.eseo.equipe2.pglback.exception.ExceptionType;
import fr.eseo.equipe2.pglback.model.Comment;
import fr.eseo.equipe2.pglback.model.User;
import fr.eseo.equipe2.pglback.payload.CommentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    @Autowired
    private CommentDao commentDao;

    @Autowired
    private UserDao userDao;

    /**
     * Find all by userID
     * @param userEmail user
     * @return corresponding user
     */
    public List<Comment> findByUserEmail(String userEmail) {
        User user = userDao.getByEmail(userEmail);
        return commentDao.findCommentsByUser(user);
    }

    /**
     * Find all comments by numberLine
     * @param numberLine
     * @return comments
     */
    public List<Comment> findByNumberLine(String numberLine) {
        List<Comment> comments = commentDao.getAllByNumberLine(numberLine);
        return comments;
    }

    /**
     * chose to save the comment in a new row or replace the old one
     * @param commentDto
     * @param userEmail
     * @return save the comment
     */
    public CommentDto register(CommentDto commentDto, String userEmail) {
        Optional<User> userReq = userDao.findByEmail(userEmail);

        if(userReq.isEmpty())
            throw exception(EntityType.USER, ExceptionType.ENTITY_NOT_FOUND);

        User user = userReq.get();

        Comment existingComment = null;
        if (commentDao.existsByUserAndNumberLine(user, commentDto.getNumberLine())) {
            existingComment = commentDao.getByUserAndNumberLine(user, commentDto.getNumberLine());
        }

        Comment comment = CommentMapper.toComment(commentDto);
        comment.setUser(user);
        if (existingComment != null)
            comment.setId(existingComment.getId());

        return CommentMapper.toCommentDto(commentDao.save(comment));
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