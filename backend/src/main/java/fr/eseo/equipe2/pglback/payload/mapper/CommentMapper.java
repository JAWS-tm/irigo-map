package fr.eseo.equipe2.pglback.dto.mapper;

import fr.eseo.equipe2.pglback.model.Comment;
import fr.eseo.equipe2.pglback.payload.CommentDto;

public class CommentMapper {
    public static CommentDto toCommentDto(Comment comment) {
        CommentDto commentDto = new CommentDto()
                .setId(comment.getId())
                .setUser(comment.getUser())
                .setNumberLine(comment.getNumberLine())
                .setNotation(comment.getNotation())
                .setCommentary(comment.getCommentary());
        return commentDto;
    }

    public static Comment toComment(CommentDto commentDto) {
        Comment comment = new Comment()
                .setId(commentDto.getId())
                .setUser(commentDto.getUser())
                .setNumberLine(commentDto.getNumberLine())
                .setNotation(commentDto.getNotation())
                .setCommentary(commentDto.getCommentary());
        return comment;
    }
}
