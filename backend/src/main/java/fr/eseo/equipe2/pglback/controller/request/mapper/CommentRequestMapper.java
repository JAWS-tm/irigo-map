package fr.eseo.equipe2.pglback.controller.request.mapper;

import fr.eseo.equipe2.pglback.controller.request.OpinionRequest;
import fr.eseo.equipe2.pglback.dto.CommentDto;

public class CommentRequestMapper {
    public static CommentDto toCommentDto(OpinionRequest opinionRequest) {
        return new CommentDto()
                .setNotation(opinionRequest.getNotation())
                .setCommentary(opinionRequest.getCommentary())
                .setNumberLine(opinionRequest.getNumberLine());
    }
}
