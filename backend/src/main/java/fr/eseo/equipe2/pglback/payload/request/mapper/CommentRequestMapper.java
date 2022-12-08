package fr.eseo.equipe2.pglback.payload.request.mapper;


import fr.eseo.equipe2.pglback.payload.CommentDto;
import fr.eseo.equipe2.pglback.payload.request.OpinionRequest;

public class CommentRequestMapper {
    public static CommentDto toCommentDto(OpinionRequest opinionRequest) {
        return new CommentDto()
                .setNotation(opinionRequest.getNotation())
                .setCommentary(opinionRequest.getCommentary())
                .setNumberLine(opinionRequest.getNumberLine());
    }
}
